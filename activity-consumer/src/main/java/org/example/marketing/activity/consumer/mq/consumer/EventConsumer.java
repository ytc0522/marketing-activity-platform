package org.example.marketing.activity.consumer.mq.consumer;

import lombok.extern.slf4j.Slf4j;
import org.example.marketing.activity.consumer.mq.Event;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@EnableBinding(Sink.class)
public class EventConsumer {


    @EventListener
    @StreamListener(Sink.INPUT)
    public void listen(Message<Event> message) {
        Event event = message.getPayload();


    }


}
