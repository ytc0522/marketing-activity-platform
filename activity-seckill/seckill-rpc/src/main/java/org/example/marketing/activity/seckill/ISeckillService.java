package org.example.marketing.activity.seckill;

import org.example.marketing.activity.seckill.dto.SeckillReq;
import org.example.marketing.activity.seckill.dto.SeckillResult;

public interface ISeckillService {

    /**
     * 预热活动，将秒杀活动中的商品放入Redis缓存中。
     * 做好活动前的准备工作。
     *
     * @param activityId
     */
    void preheat(Long activityId);

    /**
     * 秒杀商品的逻辑处理
     *
     * @param req
     * @return
     */
    SeckillResult doSeckill(SeckillReq req);

}
