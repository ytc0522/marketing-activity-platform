package marketing.activity.seckill.event.handler;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import lombok.extern.slf4j.Slf4j;
import marketing.activity.infrastructure.event.Event;
import marketing.activity.infrastructure.event.handler.IEventHandler;
import marketing.activity.seckill.domain.order.ISeckillOrderFacade;
import marketing.activity.seckill.order.dto.SeckillOrderCreateReq;
import marketing.activity.seckill.order.dto.SeckillOrderDto;
import marketing.activity.seckill.order.dto.SeckillOrderQueryReq;
import marketing.activity.seckill.order.dto.SeckillWinGoods;
import marketing.activity.seckill.service.SeckillGoodsService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * 用户成功抢到了商品，需要将缓存中的库存数据同步到数据库中
 */
@Slf4j
@Component
public class SeckillUserWinGoodsEventHandler implements IEventHandler<SeckillWinGoods> {

    @Resource
    private SeckillGoodsService goodsService;

    @Resource
    private ISeckillOrderFacade orderFacade;

    /**
     * 处理事件
     *
     * @param event
     */
    @Override
    public boolean handle(Event<SeckillWinGoods> event) {
        String jsonString = JSON.toJSONString(event);
        SeckillWinGoods goods = JSON.parseObject(jsonString, SeckillWinGoods.class);
        //Integer availableStock = goods.getAvailableStock();

//        boolean update = goodsService.lambdaUpdate().set(SeckillGoods::getAvailableCount, availableStock)
//                .eq(SeckillGoods::getActivityId, goods.getActivityId())
//                .eq(SeckillGoods::getGoodsId, goods.getGoodsId())
//                .eq(SeckillGoods::getAvailableCount, availableStock + 1)
//                .update();
//
//        log.info("同步库存:{}", update);
        // 判断用户是否重复创建订单
        SeckillOrderQueryReq req = SeckillOrderQueryReq.builder().userId(goods.getUserId())
                .goodsId(goods.getGoodsId())
                .activityId(goods.getActivityId())
                .build();
        List<SeckillOrderDto> seckillOrderDtos = orderFacade.queryOrders(req);
        if (CollectionUtils.isNotEmpty(seckillOrderDtos)) {
            // 用户已有订单
            return true;
        }
        // 创建订单
        SeckillOrderCreateReq createReq = SeckillOrderCreateReq.builder().userId(goods.getUserId()).goodsId(goods.getGoodsId())
                .activityId(goods.getActivityId()).orderId(goods.getOrderId()).build();

        boolean created = orderFacade.createSeckillOrder(createReq);
        return created;
    }


    /**
     * @return
     */
    @Override
    public Event.Type supportedEventType() {
        return Event.Type.SECKILL_USER_WIN_GOODS;
    }
}
