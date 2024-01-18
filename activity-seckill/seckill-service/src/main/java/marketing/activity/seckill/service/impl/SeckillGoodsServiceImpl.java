package marketing.activity.seckill.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import marketing.activity.seckill.repository.entity.SeckillGoods;
import marketing.activity.seckill.repository.mapper.SeckillGoodsMapper;
import marketing.activity.seckill.service.SeckillGoodsService;
import marketing.activity.seckill.util.RedisUtil;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author jack
 * @description 针对表【seckill_goods】的数据库操作Service实现
 * @createDate 2024-01-17 16:36:27
 */
@Service
public class SeckillGoodsServiceImpl extends ServiceImpl<SeckillGoodsMapper, SeckillGoods>
        implements SeckillGoodsService {


    private static String SECKILL_GOODS_PREFIX_KEY = "SECKILL:GOODS:ACTIVITYID:";

    @Resource
    private RedisUtil redisUtil;


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
        List<SeckillGoods> list = this.lambdaQuery().eq(SeckillGoods::getActivityId, activityId)
                .list();

        // 放入缓存
        redisUtil.set(key, JSON.toJSONString(list), 3600);
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




