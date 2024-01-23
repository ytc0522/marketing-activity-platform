package marketing.activity.lottery.domain.pool;

import cn.hutool.core.lang.UUID;
import lombok.extern.slf4j.Slf4j;
import marketing.activity.infrastructure.util.RedisUtil;
import marketing.lottery.rpc.constants.Constants;
import marketing.lottery.rpc.dto.LotteryActivityRich;
import marketing.lottery.rpc.dto.LotteryAwardDto;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;

/**
 * 使用Redis存放奖品和概率
 */
@Slf4j
public abstract class RedisLotteryAwardPool implements ILotteryAwardPool {

    protected static final String LOTTERY_POOL_KEY = "LOTTERY:POOL:";

    // 未中奖ID
    protected static final String NULL_AWARD_ID = "NULL";

    @Resource
    protected RedisUtil redisUtil;


    @Override
    public void refreshPool(LotteryActivityRich lotteryActivityRich) {
        log.info("正在刷新Redis 奖金池...");
        Long lotteryId = lotteryActivityRich.getLotteryActivity().getActivityId();

        String lockKey = "LOCK:POOL:REFRESH:" + lotteryId;
        String lockValue = UUID.fastUUID().toString();

        boolean locked = redisUtil.lock(lockKey, lockValue, 50);
        if (!locked) {
            return;
        }

        try {
            String lotteryPoolKey = LOTTERY_POOL_KEY + lotteryId;

            // 缓存起来
            List<LotteryAwardDto> detailList = lotteryActivityRich.getLotteryAwardlDtoList();
            detailList.forEach((e) -> {
                // 初始化 奖池
                redisUtil.zSetAdd(lotteryPoolKey, e.getAwardId(), e.getProbability().doubleValue());
            });

            // 如果奖品的总概率不足1，则需要补充空的奖品填充到总概率为1。不然概率就会不准确
            BigDecimal totalRate = detailList.stream().map(LotteryAwardDto::getProbability).reduce(BigDecimal.ZERO, BigDecimal::add);
            if (BigDecimal.ONE.compareTo(totalRate) > 0) {
                // 剩余的概率用NULL填充，表示未中奖
                BigDecimal nullRate = BigDecimal.ONE.subtract(totalRate);
                redisUtil.zSetAdd(lotteryPoolKey, Constants.NULL, nullRate.doubleValue());
            }

        } finally {
            redisUtil.unlock(lockKey, lockValue);
        }
    }

    @Override
    public void removeAward(Long activityId, String awardId) {
        redisUtil.hdel(lotteryPoolCacheKey(activityId), awardId);
    }


    private String lotteryPoolCacheKey(Long activityId) {
        return LOTTERY_POOL_KEY + activityId;
    }
}
