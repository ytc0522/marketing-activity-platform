package org.example.marketing.activity.consumer.mq.consumer;

import com.alibaba.fastjson.JSON;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.example.marketing.activity.consumer.mq.Event;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
@EnableBinding(Sink.class)
public class EventConsumer {


    @EventListener
    @StreamListener(Sink.INPUT)
    public void listen(Message<Event> message) {
        Channel channel = (Channel) message.getHeaders().get(AmqpHeaders.CHANNEL);
        Long deliveryTag = (Long) message.getHeaders().get(AmqpHeaders.DELIVERY_TAG);
        Event event = message.getPayload();
        log.info("【监听事件】{}", JSON.toJSONString(event));


        try {
            channel.basicAck(deliveryTag, false);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }


}
