package marketing.activity.seckill.order.rpc;


import cn.hutool.core.bean.BeanUtil;
import com.alibaba.dubbo.config.annotation.Service;
import marketing.activity.seckill.infrastructure.repository.entity.SeckillOrder;
import marketing.activity.seckill.order.dto.SeckillOrderCreateReq;
import marketing.activity.seckill.order.dto.SeckillOrderDto;
import marketing.activity.seckill.order.dto.SeckillOrderQueryReq;
import marketing.activity.seckill.order.service.SeckillOrderService;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.Resource;
import java.util.List;

@Service
public class SeckillOrderRpcServiceImpl implements ISeckillOrderRpcService {

    @Resource
    private SeckillOrderService orderService;


    /**
     * @param req
     */
    @Override
    public boolean createSeckillOrder(SeckillOrderCreateReq req) {
        return orderService.createSeckillOrder(req);
    }

    /**
     * @param req
     * @return
     */
    @Override
    public List<SeckillOrderDto> query(SeckillOrderQueryReq req) {
        List<SeckillOrder> list = orderService.lambdaQuery()
                .eq(StringUtils.isNotBlank(req.getUserId()), SeckillOrder::getUserId, req.getUserId())
                .eq(StringUtils.isNotBlank(req.getOrderId()), SeckillOrder::getOrderId, req.getOrderId())
                .eq(req.getGoodsId() != null, SeckillOrder::getGoodsId, req.getGoodsId())
                .eq(req.getActivityId() != null, SeckillOrder::getActivityId, req.getActivityId())
                .list();

        List<SeckillOrderDto> seckillOrderDtos = BeanUtil.copyToList(list, SeckillOrderDto.class);
        return seckillOrderDtos;
    }
}
