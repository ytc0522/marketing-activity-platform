package marketing.activity.base.mq;

import marketing.activity.infrastructure.event.Event;
import marketing.activity.infrastructure.event.handler.IEventHandler;
import org.springframework.stereotype.Component;

@Component
public class UserActivityLogHandler implements IEventHandler {


    /**
     * 处理事件
     *
     * @param event
     */
    @Override
    public boolean handle(Event event) {
        // 写入用户日志表

        return false;
    }

    /**
     * @return
     */
    @Override
    public Event.Type supportedEventType() {
        return Event.Type.USER_TAKE_ACTIVITY;
    }
}
