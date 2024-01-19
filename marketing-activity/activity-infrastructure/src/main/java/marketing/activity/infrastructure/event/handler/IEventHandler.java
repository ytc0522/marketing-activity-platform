package marketing.activity.infrastructure.event.handler;


import marketing.activity.infrastructure.event.Event;

public interface IEventHandler<T> {


    /**
     * 处理事件
     *
     * @param event
     */
    boolean handle(Event<T> event);

    Event.Type supportedEventType();


}
