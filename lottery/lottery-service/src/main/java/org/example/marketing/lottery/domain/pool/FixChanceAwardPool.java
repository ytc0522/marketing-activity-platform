package org.example.marketing.lottery.domain.pool;

import lombok.extern.slf4j.Slf4j;
import org.example.marketing.lottery.repository.util.RedisUtil;
import org.example.marketing.lottery.rpc.dto.LotteryDetailDto;
import org.example.marketing.lottery.rpc.dto.LotteryRich;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * 固定概率奖品池
 * 某个奖品抽完了之后不影响其他奖品的概率，抽中了没库存的奖品代表未中奖
 */
@Component
@Slf4j
public class FixChanceAwardPool implements IAwardPool {

    @Resource
    private RedisUtil redisUtil;

    // 奖池缓存key
    private static final String LOTTERY_POOL_KEY = "LOTTERY:POOL:";

    // 奖品库存Key
    private static final String AWARD_STOCK_KEY = "AWARD:STOCK:";

    // 未中奖ID
    private static final String NULL_AWARD_ID = "NULL";

    private static final String AWARD_ID_KEY = "awardID:";
    // 剩余库存
    private static final String SURPLUS_COUNT = "awardSurplusCount";

    @Autowired
    private StringRedisTemplate stringRedisTemplate;


    // keys-1 key  argv1 是 field  argv2 是扣减的库存
    private static final String GRAB_STOCK_SCRIPT =
            "if tonumber(redis.call('hget', KEYS[1], ARGV[1])) >= tonumber(ARGV[2]) then\n" +
            "    redis.call('hincrby', KEYS[1], ARGV[1], -tonumber(ARGV[2]))\n" +
            "    return true\n" +
            "else\n" +
            "    return false\n" +
            "end";

    private static final String TEST_SCRIPT = "local stock = redis.call('HEXISTS', KEYS[1], ARGV[1]) \n return stock";


    @Override
    public void initPool(LotteryRich lotteryRich) {
        Long lotteryId = lotteryRich.getLottery().getId();

        String lotteryPoolKey = LOTTERY_POOL_KEY + lotteryId;
        String awardStockKey = AWARD_STOCK_KEY + lotteryId;

        // 清理缓存
        boolean exists = redisUtil.hasKey(lotteryPoolKey);
        boolean awardStockExists = redisUtil.hasKey(awardStockKey);
        if (awardStockExists && exists) {
            return;
        }
        // 缓存起来
        List<LotteryDetailDto> detailList = lotteryRich.getLotteryDetailDtoList();
        detailList.stream().forEach((e) -> {
            // 初始化 奖池
            redisUtil.zSetAdd(lotteryPoolKey, e.getAwardId(), e.getAwardRate().doubleValue());
            // 初始化奖项库存
            redisUtil.hset(awardStockKey, AWARD_ID_KEY + e.getAwardId(), e.getAwardSurplusCount());
        });
    }

    @Override
    public String doDraw(Long lotteryId) {
        Set<ZSetOperations.TypedTuple<Object>> membersWithScores = redisUtil.rangeWithScores(LOTTERY_POOL_KEY + lotteryId, 0, -1);

        String awardId = NULL_AWARD_ID;

        double totalScore = 0;
        for (ZSetOperations.TypedTuple<Object> tuple : membersWithScores) {
            Double score = tuple.getScore();
            if (score == null) {
                score = 0D;
            }
            totalScore += score;
        }

        double cumulativeProbability = 0;
        double randomValue = Math.random();
        for (ZSetOperations.TypedTuple<Object> tuple : membersWithScores) {
            Double score = tuple.getScore();
            if (score == null) {
                score = 0D;
            }
            double probability = score / totalScore;
            cumulativeProbability += probability;
            if (randomValue <= cumulativeProbability) {
                awardId = String.valueOf(tuple.getValue());
                break;
            }
        }
        // 未中奖
        if (NULL_AWARD_ID.equals(awardId)) {
            return awardId;
        }

        // 中奖后要抢库存 使用LUA
        Boolean isSuccess = deductStock(lotteryId, awardId);

        if (isSuccess == null || !isSuccess) {
            log.info("扣减缓存库存失败，lotteryId:{},awardId:{}", lotteryId, awardId);
            return NULL_AWARD_ID;
        }
        return awardId;
    }

    /**
     * !!! 使用RedisTemplate 执行Lua脚本会报错。
     * @param lotteryId
     * @param awardId
     * @return
     */
    private Boolean deductStock(Long lotteryId, String awardId) {
        String awardStockKey = AWARD_STOCK_KEY + lotteryId;

        RedisScript<Boolean> redisScript = new DefaultRedisScript<>(GRAB_STOCK_SCRIPT, Boolean.class);
        return stringRedisTemplate.execute(redisScript, Collections.singletonList(awardStockKey), AWARD_ID_KEY + awardId, "1");
    }
}
