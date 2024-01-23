package marketing.activity.infrastructure.beans;

import cn.hutool.core.lang.Snowflake;
import marketing.activity.infrastructure.event.Event;
import marketing.activity.infrastructure.event.handler.IEventHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class BeanInject {

    @Bean
    public Snowflake snowflake() {
        return new Snowflake();
    }

    @Bean
    public Map<Event.Type, IEventHandler> map(List<IEventHandler> eventHandlers) {
        HashMap<Event.Type, IEventHandler> map = new HashMap<>();
        for (IEventHandler eventHandler : eventHandlers) {
            map.put(eventHandler.supportedEventType(), eventHandler);
        }
        return map;
    }


}
