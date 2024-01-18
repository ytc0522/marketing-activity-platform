package marketing.activity.seckill.event.handler.impl;

import lombok.extern.slf4j.Slf4j;
import marketing.activity.seckill.event.handler.IEventHandler;
import marketing.activity.seckill.order.dto.SeckillWinGoods;
import marketing.activity.seckill.order.mq.Event;
import marketing.activity.seckill.repository.entity.SeckillGoods;
import marketing.activity.seckill.service.SeckillGoodsService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 用户成功抢到了商品，需要将缓存中的库存数据同步到数据库中
 */
@Slf4j
@Component
public class SeckillUserWinGoodsEventHandler implements IEventHandler<SeckillWinGoods> {

    @Resource
    private SeckillGoodsService goodsService;

    /**
     * 处理事件
     *
     * @param event
     */
    @Override
    public void handle(Event<SeckillWinGoods> event) {
        SeckillWinGoods goods = event.getBody();
        Integer availableStock = goods.getAvailableStock();

        boolean update = goodsService.lambdaUpdate().set(SeckillGoods::getAvailableCount, availableStock)
                .eq(SeckillGoods::getActivityId, goods.getActivityId())
                .eq(SeckillGoods::getGoodsId, goods.getGoodsId())
                .update();

        log.info("同步库存:{}", update);

    }


    /**
     * @return
     */
    @Override
    public Event.Type supportedEventType() {
        return Event.Type.SECKILL_USER_WIN_GOODS;
    }
}
