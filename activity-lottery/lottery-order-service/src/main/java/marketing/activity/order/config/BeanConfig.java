package marketing.activity.order.config;

import marketing.activity.order.event.handler.IEventHandler;
import org.example.marketing.common.mq.Event;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
public class BeanConfig {


    @Bean
    public Map<Event.Type, IEventHandler> orderHandlers(List<IEventHandler> handlerList) {
        Map<Event.Type, IEventHandler> handlerMap = new HashMap<>();
        handlerList.forEach(handler -> handlerMap.put(handler.supportedEventType(), handler));
        return handlerMap;
    }


}
