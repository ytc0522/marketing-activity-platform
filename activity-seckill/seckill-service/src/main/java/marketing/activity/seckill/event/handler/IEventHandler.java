package marketing.activity.seckill.event.handler;


import marketing.activity.seckill.order.mq.Event;

public interface IEventHandler<T> {


    /**
     * 处理事件
     *
     * @param event
     */
    void handle(Event<T> event);

    Event.Type supportedEventType();


}
