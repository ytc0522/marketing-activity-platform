package org.example.marketing.lottery.domain.pool;

import cn.hutool.core.lang.UUID;
import lombok.extern.slf4j.Slf4j;
import org.example.marketing.lottery.repository.util.RedisUtil;
import org.example.marketing.lottery.rpc.constants.Constants;
import org.example.marketing.lottery.rpc.dto.LotteryDetailDto;
import org.example.marketing.lottery.rpc.dto.LotteryRich;
import org.example.marketing.lottery.service.LotteryService;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

/**
 * 使用Redis存放奖品和概率
 */
@Slf4j
public abstract class RedisLotteryAwardPool implements ILotteryAwardPool,IAwardStock {

    protected static final String LOTTERY_POOL_KEY = "LOTTERY:POOL:";

    // 奖品库存Key
    protected static final String AWARD_STOCK_KEY = "LOTTERY:AWARD:STOCK:";

    // 未中奖ID
    protected static final String NULL_AWARD_ID = "NULL";

    protected static final String AWARD_ID_KEY = "AWARD_ID:";


    // keys-1 key  argv1 是 field  argv2 是扣减的库存
    protected static final String GRAB_STOCK_SCRIPT =
            "if tonumber(redis.call('hget', KEYS[1], ARGV[1])) >= tonumber(ARGV[2]) then\n" +
                    "    return redis.call('hincrby', KEYS[1], ARGV[1], -tonumber(ARGV[2]))\n" +
                    "else\n" +
                    "    return tonumber(redis.call('hget', KEYS[1], ARGV[1]))\n" +
                    "end";


    @Resource
    protected RedisUtil redisUtil;

    @Resource
    protected LotteryService lotteryService;


    @Override
    public void refreshPool(LotteryRich lotteryRich) {
        log.info("正在刷新Redis 奖金池...");
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
                redisUtil.hset(awardStockKey, e.getAwardId(), e.getAwardSurplusCount());
            });

            // 如果奖品的总概率不足1，则需要补充空的奖品填充到总概率为1。不然概率就会不准确
            BigDecimal totalRate = detailList.stream().map(LotteryDetailDto::getAwardRate).reduce(BigDecimal.ZERO, BigDecimal::add);
            if (BigDecimal.ONE.compareTo(totalRate) > 0) {
                // 剩余的概率用NULL填充，表示未中奖
                BigDecimal nullRate = BigDecimal.ONE.subtract(totalRate);
                redisUtil.zSetAdd(lotteryPoolKey, Constants.NULL, nullRate.doubleValue());
            }

        } finally {
            redisUtil.unlock(lockKey, lockValue);
        }
    }

    /**
     * 这里因为分布式锁没有续期功能，可能会出现多个线程同时更新数据库，这样依然会造成数据覆盖问题，
     * 但是，这个概率非常低，同时，就算出现了这种情况，也不会影响业务。因为库存是以Redis中数据为准，不会出现超卖就行。
     * @param lotteryId
     * @param awardId
     * @return
     */
    @Override
    public Long deductStock(Long lotteryId, String awardId) {
        String lockKey = "LOCK_LOTTERY:" + lotteryId + "AWARD:" + awardId;
        String lockVal = UUID.fastUUID().toString();
        boolean lockSuccess = redisUtil.lock(lockKey, lockVal, 1000);
        if (!lockSuccess) {
            log.info("分布式锁加锁失败,表示抽奖奖品无效");
            return -1L;
        }
        boolean isSuccess = false;
        Long cacheStock = -1L;
        try {
            String awardStockKey = AWARD_STOCK_KEY + lotteryId;

            // todo 不知道Redis 扣减库存成功与否，需要优化。
            RedisScript<Long> redisScript = new DefaultRedisScript<>(GRAB_STOCK_SCRIPT, Long.class);
            cacheStock = redisUtil.stringRedisTemplate().execute(redisScript, Collections.singletonList(awardStockKey), awardId, "1");

            // 将缓存中的库存数据同步给数据库 todo 不能每次都刷新到数据库，应该在活动结束后，将缓存库存刷新到数据库。
            isSuccess = lotteryService.updateStockCount(lotteryId, awardId, cacheStock);
            // 如果缓存扣减成功，但是 数据库同步失败
            if (!isSuccess) {
                log.info("Cache库存扣减成功,DB库存扣减失败,回滚Cache库存");
                cacheStock = redisUtil.hincr(awardStockKey, awardId, 1L);
            }
        } catch (Exception e) {
            log.error("扣减库存异常", e);
        } finally {
            redisUtil.unlock(lockKey, lockVal);
        }
        return cacheStock;
    }
}
