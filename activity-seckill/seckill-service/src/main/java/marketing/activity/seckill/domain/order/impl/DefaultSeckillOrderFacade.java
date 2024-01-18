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
     *
     *
     * @param queryReq
     * @return
     */
    @Override
    public List<SeckillOrderDto> queryOrders(SeckillOrderQueryReq queryReq) {
        return orderRpcService.query(queryReq);
    }


    /**
     * @param createReq
     * @return
     */
    @Override
    public boolean createSeckillOrder(SeckillOrderCreateReq createReq) {
        return orderRpcService.createSeckillOrder(createReq);
    }
}
