package marketing.activity.seckill.order.rpc;


import marketing.activity.seckill.order.dto.SeckillOrderCreateReq;
import marketing.activity.seckill.order.dto.SeckillOrderDto;
import marketing.activity.seckill.order.dto.SeckillOrderQueryReq;

import java.util.List;

public interface ISeckillOrderRpcService {


    boolean createSeckillOrder(SeckillOrderCreateReq req);

    List<SeckillOrderDto> query(SeckillOrderQueryReq req);

}
