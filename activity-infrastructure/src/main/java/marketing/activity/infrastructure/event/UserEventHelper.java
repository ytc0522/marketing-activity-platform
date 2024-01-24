package marketing.activity.infrastructure.event;

import cn.hutool.core.lang.Snowflake;
import marketing.activity.infrastructure.event.body.UserActivityLogEventBody;
import marketing.activity.infrastructure.mq.producer.EventProducer;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class UserEventHelper {

    @Resource
    private EventProducer eventProducer;

    @Resource
    private Snowflake snowflake;


    /**
     * 发布用户参加活动事件
     *
     * @return
     */
    public String publishUserTakeActivityEvent(UserActivityLogEventBody body) {
        Event<Object> event = new Event<>();
        event.setType(Event.Type.USER_TAKE_ACTIVITY);
        event.setBody(body);
        event.setEventId(snowflake.nextIdStr());
        return eventProducer.publish(event) ? event.getEventId() : null;
    }


}
