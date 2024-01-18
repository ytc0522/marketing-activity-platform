package marketing.activity.seckill;

import marketing.activity.seckill.dto.ActionResult;
import marketing.activity.seckill.dto.SeckillReq;

public interface ISeckillRpcService {

    /**
     * 预热活动，将秒杀活动中的商品放入Redis缓存中。
     * 做好活动前的准备工作。
     *
     * @param activityId
     */
    void preheat(Long activityId);

    /**
     * 秒杀商品的逻辑处理 异步处理
     *
     * @param req
     * @return
     */
    ActionResult seckillAsync(SeckillReq req);


    /**
     * 秒杀商品的同步处理
     *
     * @return
     */
    ActionResult seckillSync(SeckillReq seckillReq);

    /**
     * 获取秒杀结果
     *
     * @param req
     * @param token
     * @return
     */
    ActionResult seckillResult(SeckillReq req, String token);
}
