package marketing.activity.infrastructure.event;


import lombok.extern.slf4j.Slf4j;
import marketing.activity.infrastructure.event.handler.IEventHandler;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Map;

@Slf4j
@Component
public class EventProcess {

    @Resource
    private Map<Event.Type, IEventHandler> handlerMap;


    public boolean process(Event event) {
        IEventHandler iEventHandler = handlerMap.get(event.getType());
        if (iEventHandler == null) {
            return true;
        }
        boolean handled = iEventHandler.handle(event);

        log.info("【事件：{}】处理完毕!", event.getEventId());

        return handled;
    }


}
