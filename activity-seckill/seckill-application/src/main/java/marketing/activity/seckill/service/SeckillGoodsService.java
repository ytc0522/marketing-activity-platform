package marketing.activity.seckill.service;

import com.baomidou.mybatisplus.extension.service.IService;
import marketing.activity.seckill.infrastructure.repository.entity.SeckillGoods;

import java.util.List;

/**
 * @author jack
 * @description 针对表【seckill_goods】的数据库操作Service
 * @createDate 2024-01-17 16:36:27
 */
public interface SeckillGoodsService extends IService<SeckillGoods> {


    /**
     * 根据活动ID查询活动商品，先从缓存中查询，如果不存在，则查询数据库，并放入到缓存中
     *
     * @param activityId 活动ID
     * @return
     */
    List<SeckillGoods> queryByActivityId(Long activityId);


    /**
     * 根据Id查找秒杀商品信息
     *
     * @param activityId
     * @param goodsId
     * @return
     */
    SeckillGoods querySeckillGoods(Long activityId, String goodsId);



}
