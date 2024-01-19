package marketing.activity.order.event.handler.impl;

import lombok.extern.slf4j.Slf4j;
import marketing.activity.infrastructure.event.Event;
import marketing.activity.infrastructure.event.handler.IEventHandler;
import org.springframework.stereotype.Component;

/**
 * 订单创建事件处理器
 */
@Slf4j
@Component
public class ActivityOrderCreatedEventHandler implements IEventHandler {


    /**
     * 当订单创建了，应该对订单进行风控检查。
     *
     * @param event 事件
     */
    @Override
    public boolean handle(Event event) {
        String orderId = event.getBody().toString();
        // todo 风控检查
        log.info("模拟对订单进行风控检查,{}", orderId);
        return true;
    }

    @Override
    public Event.Type supportedEventType() {
        return Event.Type.ACTIVITY_ORDER_CREATED;
    }


}
