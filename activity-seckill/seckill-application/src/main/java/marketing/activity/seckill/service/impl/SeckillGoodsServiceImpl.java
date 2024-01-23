package marketing.activity.seckill.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import marketing.activity.infrastructure.util.RedisUtil;
import marketing.activity.seckill.infrastructure.repository.entity.SeckillGoods;
import marketing.activity.seckill.infrastructure.repository.mapper.SeckillGoodsMapper;
import marketing.activity.seckill.service.SeckillGoodsService;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author jack
 * @description 针对表【seckill_goods】的数据库操作Service实现
 * @createDate 2024-01-17 16:36:27
 */
@Service
@Slf4j
public class SeckillGoodsServiceImpl extends ServiceImpl<SeckillGoodsMapper, SeckillGoods>
        implements SeckillGoodsService {


    private static String SECKILL_GOODS_PREFIX_KEY = "SECKILL:GOODS:ACTIVITYID:";

    @Resource
    private RedisUtil redisUtil;

    @Resource
    private RedissonClient redissonClient;


    public String goodsCacheKey(Long activityId) {
        return SECKILL_GOODS_PREFIX_KEY + activityId;
    }


    /**
     * @param activityId
     * @return
     */
    @Override
    public List<SeckillGoods> queryByActivityId(Long activityId) {

        // 查询缓存
        String key = goodsCacheKey(activityId);
        Object value = redisUtil.get(key);
        if (value != null) {
            List<SeckillGoods> seckillGoods = JSON.parseArray((String) value, SeckillGoods.class);
            return seckillGoods;
        }

        // 从数据库查询
        String lockKey = "lock_seckill_goods:" + activityId;
        RLock lock = redissonClient.getLock(lockKey);

        List<SeckillGoods> list = null;

        try {

            // 加锁的目的是防止缓存击穿，避免大量请求击垮数据库
            lock.lock(1, TimeUnit.SECONDS);
            list = this.lambdaQuery().eq(SeckillGoods::getActivityId, activityId)
                    .list();

            // 放入缓存
            redisUtil.set(key, JSON.toJSONString(list), 3600);

        } catch (Exception e) {
            throw e;
        } finally {
            if (lock.isLocked()) {
                lock.unlock();
            }
        }


        return list;
    }

    /**
     * 根据Id查找秒杀商品信息
     *
     * @param activityId
     * @param goodsId
     * @return
     */
    @Override
    public SeckillGoods querySeckillGoods(Long activityId, String goodsId) {
        List<SeckillGoods> seckillGoods = queryByActivityId(activityId);
        if (CollectionUtils.isEmpty(seckillGoods)) {
            return null;
        }
        SeckillGoods found = seckillGoods.stream().filter(e -> e.getGoodsId().equals(goodsId)).findFirst().orElse(null);
        return found;
    }
}




