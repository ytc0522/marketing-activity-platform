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
     * 从数据库中查询订单
     *
     * @param queryReq
     * @return
     */
    List<SeckillOrderDto> queryFromDB(SeckillOrderQueryReq queryReq);


    String createSeckillOrder(SeckillOrderCreateReq createReq);

}
