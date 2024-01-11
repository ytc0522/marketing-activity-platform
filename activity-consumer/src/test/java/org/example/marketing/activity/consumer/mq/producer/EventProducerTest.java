package org.example.marketing.activity.consumer.mq.producer;

import org.example.marketing.common.mq.Event;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EventProducerTest {

    @Resource
    private EventProducer eventProducer;

    @Test
    public void publish() {
        Event event = new Event();
        event.setType(Event.Type.ACTIVITY_ORDER_CREATED);
        event.setBody("e250eaa9-39a6-4955-b410-ba5d8d9f3210");
        boolean published = eventProducer.publish(event);


    }
}