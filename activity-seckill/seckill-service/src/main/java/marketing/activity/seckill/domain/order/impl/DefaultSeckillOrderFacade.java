package marketing.activity.seckill.domain.order.impl;

import marketing.activity.seckill.domain.order.ISeckillOrderFacade;
import marketing.activity.seckill.order.dto.SeckillOrderCreateReq;
import marketing.activity.seckill.order.dto.SeckillOrderDto;
import marketing.activity.seckill.order.dto.SeckillOrderQueryReq;
import marketing.activity.seckill.order.rpc.ISeckillOrderRpcService;
import marketing.activity.seckill.util.RedisUtil;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Component
public class DefaultSeckillOrderFacade implements ISeckillOrderFacade {

    @DubboReference
    private ISeckillOrderRpcService orderRpcService;

    @Resource
    private RedisUtil redisUtil;

    /**
     * @param queryReq
     * @return
     */
    @Override
    public List<SeckillOrderDto> queryFromDB(SeckillOrderQueryReq queryReq) {
        return orderRpcService.query(queryReq);
    }


    private String tokenCacheKey(Long activityId, String userId, String goodsId) {
        return "Seckill:Token:" + activityId + ":" + userId + ":" + goodsId;
    }

    /**
     * @param createReq
     * @return
     */
    @Override
    public String createSeckillOrder(SeckillOrderCreateReq createReq) {
        return orderRpcService.createSeckillOrder(createReq);
    }
}
