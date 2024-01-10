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

    public boolean publish(Event event) {
        boolean published = false;
        try {
            String msg = JSON.toJSONString(event);
            Message<String> message;
            if (event.isDelayConsume()) {
                message = MessageBuilder.withPayload(msg).setHeader("x-delay", (event).getDelayTime()).build();
            } else {
                message = MessageBuilder.withPayload(msg).build();
            }
            log.info("【事件发布】{}", message);
            published = source.output().send(message);
            if (!published) {
                log.info("【事件发布失败】：" + event.getEventId());
            }
        } catch (Exception e) {
            log.error("【事件发送异常】{}", event.getEventId(), e);
        }


        return published;
    }


}
