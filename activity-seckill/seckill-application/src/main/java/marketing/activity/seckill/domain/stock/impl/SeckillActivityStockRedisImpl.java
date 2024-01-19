package marketing.activity.seckill.domain.stock.impl;


import lombok.extern.slf4j.Slf4j;
import marketing.activity.infrastructure.util.RedisUtil;
import marketing.activity.seckill.domain.stock.ISeckillActivityStock;
import marketing.activity.seckill.infrastructure.repository.entity.SeckillGoods;
import marketing.activity.seckill.service.SeckillGoodsService;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;

@Slf4j
@Service
public class SeckillActivityStockRedisImpl implements ISeckillActivityStock {

    @Resource
    private SeckillGoodsService goodsService;

    @Resource
    private RedisUtil redisUtil;

    // keys-1 key  argv1 是 field  argv2 是扣减的库存
    protected static final String GRAB_STOCK_SCRIPT =
            "if tonumber(redis.call('hget', KEYS[1], ARGV[1])) >= tonumber(ARGV[2]) then\n" +
                    "    redis.call('hincrby', KEYS[1], ARGV[1], -tonumber(ARGV[2]))\n" +
                    "    return 1 \n" +
                    "else\n" +
                    "    return 0\n" +
                    "end";


    private String stockCacheKey(Long activityId) {
        return "Seckill:Stock:" + activityId;
    }

    /**
     * 初始化库存
     *
     * @param activityId
     */
    @Override
    public void beforeActivityStart(Long activityId) {
        String stockCacheKey = stockCacheKey(activityId);
        Object value = redisUtil.get(stockCacheKey);
        if (value != null) {
            return;
        }
        List<SeckillGoods> seckillGoods = goodsService.queryByActivityId(activityId);
        if (CollectionUtils.isEmpty(seckillGoods)) {
            return;
        }
        for (SeckillGoods seckillGood : seckillGoods) {
            redisUtil.hset(stockCacheKey, seckillGood.getGoodsId(), seckillGood.getAvailableCount());
        }
    }

    /**
     * 扣减库存
     *
     * @param activityId
     * @param goodsId
     * @return
     */
    @Override
    public boolean deductStock(Long activityId, String goodsId) {
        RedisScript<Long> redisScript = new DefaultRedisScript<>(GRAB_STOCK_SCRIPT, Long.class);
        Long result = redisUtil.stringRedisTemplate()
                .execute(redisScript, Collections.singletonList(stockCacheKey(activityId)),
                        goodsId, "1");

        return result != null && result == 1;
    }

    /**
     * 查询库存
     *
     * @param activityId
     * @param goodsId
     * @return
     */
    @Override
    public Integer queryStock(Long activityId, String goodsId) {
        Integer count = (Integer) redisUtil.hget(stockCacheKey(activityId), goodsId);
        return count;
    }

    /**
     * 回滚库存
     *
     * @param activityId
     * @param goodsId
     * @return
     */
    @Override
    public boolean rollBack(Long activityId, String goodsId) {
        redisUtil.hincr(stockCacheKey(activityId), goodsId, 1L);
        return true;
    }

    /**
     * @param activityId
     */
    @Override
    public void onActivityDone(Long activityId) {
        redisUtil.del(stockCacheKey(activityId));
    }
}
