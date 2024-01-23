package marketing.activity.lottery.domain.stock;

import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import lombok.extern.slf4j.Slf4j;
import marketing.activity.infrastructure.util.RedisUtil;
import marketing.activity.lottery.infrastructure.repository.entity.LotteryAward;
import marketing.activity.lottery.infrastructure.repository.mapper.LotteryActivityMapper;
import marketing.activity.lottery.infrastructure.repository.mapper.LotteryAwardMapper;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;

/**
 * 使用Redis存储库存
 */
@Slf4j
@Service
public class RedisAwardStockStorage implements ILotteryAwardStockStorage {

    protected static final String LOTTERY_AWARD_STOCK = "LOTTERY:AWARD:STOCK:";

    // keys-1 key  argv1 是 field  argv2 是扣减的库存
    protected static final String GRAB_STOCK_SCRIPT =
            "if tonumber(redis.call('hget', KEYS[1], ARGV[1])) >= tonumber(ARGV[2]) then\n" +
                    "    redis.call('hincrby', KEYS[1], ARGV[1], -tonumber(ARGV[2]))\n" +
                    "    return 1 \n" +
                    "else\n" +
                    "    return 0\n" +
                    "end";

    @Resource
    protected RedisUtil redisUtil;

    @Resource
    private LotteryActivityMapper lotteryActivityMapper;

    @Resource
    private LotteryAwardMapper lotteryAwardMapper;


    /**
     * 刷新抽奖活动的库存,将Redis中缓存的活动库存重新更新为数据库中的库存
     *
     * @param activityId
     * @return
     */
    @Override
    public void refreshStock(Long activityId) {
        List<LotteryAward> list = new LambdaQueryChainWrapper<LotteryAward>(lotteryAwardMapper)
                .eq(LotteryAward::getActivityId, activityId).list();

        String lotteryStockCacheKey = getLotteryStockCacheKey(activityId);
        list.forEach((e) -> {
            // 初始化奖项库存
            redisUtil.hset(lotteryStockCacheKey, e.getAwardId(), e.getAvailableCount());
        });
    }

    /**
     * 只抢缓存中的库存，不需要加锁
     *
     * @param activityId
     * @param awardId
     * @return
     */
    @Override
    public boolean deductStock(Long activityId, String awardId) {

        boolean isCutSuccess = false;
        try {

            RedisScript<Long> redisScript = new DefaultRedisScript<>(GRAB_STOCK_SCRIPT, Long.class);
            Long result = redisUtil.stringRedisTemplate()
                    .execute(redisScript, Collections.singletonList(getLotteryStockCacheKey(activityId)),
                            awardId, "1");
            isCutSuccess = (result != null && result == 1);
        } catch (Exception e) {
            log.error("扣减库存异常", e);
        }
        return isCutSuccess;
    }

    @Override
    public boolean rollBack(Long activityId, String awardId) {
        String lotteryStockCacheKey = getLotteryStockCacheKey(activityId);
        redisUtil.hincr(lotteryStockCacheKey, awardId, 1L);
        return false;
    }


    private String getLotteryStockCacheKey(long activityId) {
        return LOTTERY_AWARD_STOCK + activityId;
    }
}
