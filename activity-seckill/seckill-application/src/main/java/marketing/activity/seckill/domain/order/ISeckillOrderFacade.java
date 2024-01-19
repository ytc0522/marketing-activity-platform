package marketing.activity.seckill.domain.order;

import marketing.activity.seckill.order.dto.SeckillOrderCreateReq;
import marketing.activity.seckill.order.dto.SeckillOrderDto;
import marketing.activity.seckill.order.dto.SeckillOrderQueryReq;

import java.util.List;

/**
 * 秒杀订单处理器
 */
public interface ISeckillOrderFacade {

    /**
     * 查询订单
     *
     * @param queryReq
     * @return
     */
    List<SeckillOrderDto> queryOrders(SeckillOrderQueryReq queryReq);

    boolean createSeckillOrder(SeckillOrderCreateReq createReq);

}
