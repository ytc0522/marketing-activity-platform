package marketing.activity.seckill.order.rpc;


import com.alibaba.dubbo.config.annotation.Service;
import marketing.activity.seckill.order.dto.SeckillOrderCreateReq;
import marketing.activity.seckill.order.repository.entity.SeckillOrder;
import marketing.activity.seckill.order.repository.mapper.SeckillOrderMapper;

import javax.annotation.Resource;
import java.util.Date;

@Service
public class SeckillOrderRpcServiceImpl implements ISeckillOrderRpcService {

    @Resource
    private SeckillOrderMapper seckillOrderMapper;

    /**
     * @param req
     */
    @Override
    public String createSeckillOrder(SeckillOrderCreateReq req) {
        SeckillOrder seckillOrder = new SeckillOrder();
        seckillOrder.setOrderStatus("0");
        seckillOrder.setActivityId(req.getActivityId());
        seckillOrder.setOrderId(null);
        seckillOrder.setGoodsId(req.getGoodsId());
        seckillOrder.setGoodsName("商品ID：" + req.getGoodsId());
        seckillOrder.setUserId(req.getUserId());

        seckillOrder.setCreateTime(new Date());
        seckillOrder.setUpdateTime(new Date());

        seckillOrderMapper.insert(seckillOrder);

        return seckillOrder.getOrderId();
    }
}
