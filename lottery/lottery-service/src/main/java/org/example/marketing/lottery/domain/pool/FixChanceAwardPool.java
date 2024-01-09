package org.example.marketing.lottery.domain.pool;

import cn.hutool.core.lang.UUID;
import lombok.extern.slf4j.Slf4j;
import org.example.marketing.lottery.repository.util.RedisUtil;
import org.example.marketing.lottery.rpc.constants.Constants;
import org.example.marketing.lottery.rpc.dto.LotteryDetailDto;
import org.example.marketing.lottery.rpc.dto.LotteryRich;
import org.example.marketing.lottery.service.LotteryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * 固定概率奖品池
 * 某个奖品抽完了之后不影响其他奖品的概率，抽中了没库存的奖品代表未中奖
 */
@Component
@Slf4j
public class FixChanceAwardPool implements IAwardPool,IAwardStock {

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

    @Resource
    private LotteryService lotteryService;


    // keys-1 key  argv1 是 field  argv2 是扣减的库存
    private static final String GRAB_STOCK_SCRIPT =
            "if tonumber(redis.call('hget', KEYS[1], ARGV[1])) >= tonumber(ARGV[2]) then\n" +
            "    return redis.call('hincrby', KEYS[1], ARGV[1], -tonumber(ARGV[2]))\n" +
            "else\n" +
            "    return -1\n" +
            "end";

    private static final String TEST_SCRIPT = "local stock = redis.call('HEXISTS', KEYS[1], ARGV[1]) \n return stock";


    private boolean isInit = false;

    /**
     * 为了保证刷新时的数据一致性，需要加锁
     * @param lotteryRich
     */
    @Override
    public void refreshPool(LotteryRich lotteryRich) {
        log.info("正在刷新奖金池...");
        Long lotteryId = lotteryRich.getLottery().getId();

        String lockKey = "LOCK:POOL:REFRESH:" + lotteryId;
        String lockValue = UUID.fastUUID().toString();

        boolean locked = redisUtil.lock(lockKey, lockValue, 50);
        if (!locked) {
            return;
        }

        try {
            String lotteryPoolKey = LOTTERY_POOL_KEY + lotteryId;
            String awardStockKey = AWARD_STOCK_KEY + lotteryId;

            // 清理缓存
            boolean exists = redisUtil.hasKey(lotteryPoolKey);
            boolean awardStockExists = redisUtil.hasKey(awardStockKey);
            if (awardStockExists && exists) {
                redisUtil.del(lotteryPoolKey);
                redisUtil.del(awardStockKey);
            }
            // 缓存起来
            List<LotteryDetailDto> detailList = lotteryRich.getLotteryDetailDtoList();
            detailList.stream().forEach((e) -> {
                // 初始化 奖池
                redisUtil.zSetAdd(lotteryPoolKey, e.getAwardId(), e.getAwardRate().doubleValue());
                // 初始化奖项库存
                redisUtil.hset(awardStockKey, AWARD_ID_KEY + e.getAwardId(), e.getAwardSurplusCount());
            });

            // 如果奖品的总概率不足1，则需要补充空的奖品填充到总概率为1。不然概率就会不准确
            BigDecimal totalRate = detailList.stream().map(LotteryDetailDto::getAwardRate).reduce(BigDecimal.ZERO, BigDecimal::add);
            if (BigDecimal.ONE.compareTo(totalRate) > 0) {
                // 剩余的概率用NULL填充，表示未中奖
                BigDecimal nullRate = BigDecimal.ONE.subtract(totalRate);
                redisUtil.zSetAdd(lotteryPoolKey, Constants.NULL,nullRate.doubleValue());
            }

        } finally {
            redisUtil.unlock(lockKey,lockValue);
        }
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
        boolean isSuccess = deductStock(lotteryId, awardId);

        if (!isSuccess) {
            log.info("扣减库存失败等于未中奖，lotteryId:{},awardId:{}", lotteryId, awardId);
            return NULL_AWARD_ID;
        }
        return awardId;
    }

    /**
     * !!! 使用RedisTemplate 执行Lua脚本会报错。
     * 为了保证数据库
     * @param lotteryId
     * @param awardId
     * @return
     */
    @Override
    public boolean deductStock(Long lotteryId, String awardId) {
        String lockKey = "LOCK_LOTTERY:" + lotteryId + "AWARD:" + awardId;
        String lockVal = UUID.fastUUID().toString();
        boolean lockSuccess = redisUtil.lock(lockKey, lockVal,1000);
        if (lockSuccess) {
            log.info("分布式锁加锁失败,表示抽奖奖品无效");
            return false;
        }
        boolean isSuccess =false;
        try {
            String awardStockKey = AWARD_STOCK_KEY + lotteryId;

            RedisScript<Long> redisScript = new DefaultRedisScript<>(GRAB_STOCK_SCRIPT, Long.class);
            Long cacheStock = stringRedisTemplate.execute(redisScript, Collections.singletonList(awardStockKey), AWARD_ID_KEY + awardId, "1");
            // 缓存库存扣减失败
            if (cacheStock < 0) {
                return isSuccess;
            }
            // 将缓存中的库存数据同步给数据库
            isSuccess = lotteryService.updateStockCount(lotteryId, awardId, cacheStock);
            // 如果缓存扣减成功，但是 数据库同步失败
            if (!isSuccess) {
                log.info("Cache库存扣减成功,DB库存扣减失败,回滚Cache库存");
                redisUtil.hincr(awardStockKey,AWARD_ID_KEY + awardId,1);
            }
        } catch (Exception e) {
             log.error("扣减库存异常",e);
        } finally {
            redisUtil.unlock(lockKey,lockVal);
        }

        return isSuccess;

    }
}
