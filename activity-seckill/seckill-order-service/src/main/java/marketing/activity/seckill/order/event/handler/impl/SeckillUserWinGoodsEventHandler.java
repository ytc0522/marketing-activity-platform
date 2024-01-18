package marketing.activity.seckill.order.event.handler.impl;

import cn.hutool.core.lang.Snowflake;
import lombok.extern.slf4j.Slf4j;
import marketing.activity.seckill.order.dto.SeckillOrderCreateReq;
import marketing.activity.seckill.order.dto.SeckillWinGoods;
import marketing.activity.seckill.order.event.handler.IEventHandler;
import marketing.activity.seckill.order.mq.Event;
import marketing.activity.seckill.order.service.SeckillOrderService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 用户成功抢到了商品，需要将缓存中的库存数据同步到数据库中
 */
@Slf4j
@Component
public class SeckillUserWinGoodsEventHandler implements IEventHandler<SeckillWinGoods> {


    @Resource
    private SeckillOrderService orderService;

    private Snowflake snowflake = new Snowflake(1);

    /**
     * 处理事件
     *
     * @param event
     */
    @Override
    public void handle(Event<SeckillWinGoods> event) {
        SeckillWinGoods goods = event.getBody();
        // 判断用户订单数量，判断用户没有重复下单。

        SeckillOrderCreateReq req = SeckillOrderCreateReq.builder()
                .userId(goods.getUserId())
                .activityId(goods.getActivityId())
                .goodsId(goods.getGoodsId())
                .orderId(snowflake.nextIdStr())
                .build();
        boolean created = orderService.createSeckillOrder(req);
        if (!created) {
            log.error("保存订单失败");
        }
    }


    /**
     * @return
     */
    @Override
    public Event.Type supportedEventType() {
        return Event.Type.SECKILL_USER_WIN_GOODS;
    }
}
