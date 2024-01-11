package org.example.marketing.activity.order.event.handler.impl;

import org.example.marketing.activity.order.event.handler.IEventHandler;
import org.example.marketing.common.mq.Event;
import org.springframework.stereotype.Component;

@Component
public class ActivityOrderCreatedEventHandler implements IEventHandler {


    @Override
    public void handle(Event event) {

    }

    @Override
    public Event.Type supportedEventType() {
        return Event.Type.ACTIVITY_ORDER_CREATED;
    }


}
