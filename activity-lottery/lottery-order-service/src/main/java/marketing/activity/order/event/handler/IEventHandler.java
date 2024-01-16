package marketing.activity.order.event.handler;

import org.example.marketing.common.mq.Event;

public interface IEventHandler {


    /**
     * 处理事件
     *
     * @param event
     */
    void handle(Event event);

    Event.Type supportedEventType();


}
