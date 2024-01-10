package org.example.marketing.activity.consumer.mq.producer;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.example.marketing.activity.consumer.mq.Event;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;


@Slf4j
@Component
@EnableBinding(Source.class)
public class EventProducer {

    @Resource
    private Source source;

    public void publish(Event event) {
        String msg = JSON.toJSONString(event);
        Message<String> message;
        if (event.isDelayConsume()) {
            message = MessageBuilder.withPayload(msg).setHeader("x-delay", (event).getDelayTime()).build();
        } else {
            message = MessageBuilder.withPayload(msg).build();
        }
        boolean published = source.output().send(message);
        if (published) {
            log.info("订单事件发送成功：" + msg);
        } else {
            log.info("订单事件发送失败：" + msg);
        }
    }


}
