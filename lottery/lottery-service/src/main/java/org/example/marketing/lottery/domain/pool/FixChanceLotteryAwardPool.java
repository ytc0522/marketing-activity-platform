package org.example.marketing.lottery.domain.pool;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * 固定概率奖品池
 * 某个奖品抽完了之后不影响其他奖品的概率，抽中了没库存的奖品代表未中奖
 */
@Component
@Slf4j
public class FixChanceLotteryAwardPool extends RedisLotteryAwardPool implements IAwardStock {

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
        Long stock = deductStock(lotteryId, awardId);

        if (stock == -1L) {
            log.info("扣减库存失败等于未中奖，lotteryId:{},awardId:{}", lotteryId, awardId);
            return NULL_AWARD_ID;
        }
        return awardId;
    }

}
