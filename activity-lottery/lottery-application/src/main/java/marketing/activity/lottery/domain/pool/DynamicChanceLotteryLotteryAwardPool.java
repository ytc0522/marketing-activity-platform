package marketing.activity.lottery.domain.pool;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * 动态概率奖品池：
 * 某个奖品抽完了之后，会将该奖品从奖池中拿出，这样其他奖品的概率就会提高
 */
@Slf4j
@Component
public class DynamicChanceLotteryLotteryAwardPool extends RedisLotteryAwardPool {

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

        return awardId;
    }
}
