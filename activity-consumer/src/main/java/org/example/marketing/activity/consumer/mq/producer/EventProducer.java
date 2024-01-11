package org.example.marketing.activity.consumer.mq.producer;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.example.activity.repository.entity.MqMsgSendFailRecord;
import org.example.marketing.activity.consumer.service.MqMsgSendFailRecordService;
import org.example.marketing.common.mq.Event;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;


@Slf4j
@Component
@EnableBinding(Source.class)
public class EventProducer {

    @Resource
    private Source source;

    @Resource
    private MqMsgSendFailRecordService recordService;

    public boolean publish(Event event) {
        boolean published = false;
        try {
            String msg = JSON.toJSONString(event);
            Message<String> message = MessageBuilder.withPayload(msg).build();
            log.info("【事件发布】{}", message);
            published = source.output().send(message);
        } catch (Exception e) {
            log.error("【事件发送异常】{}", event.getEventId(), e);
        }
        return published;
    }

    /**
     * 发送失败自动记录该消息到表：mq_msg_send_fail_record
     *
     * @return
     */
    public void publishWithRecord(Event event) {
        boolean published = publish(event);
        if (!published) {
            MqMsgSendFailRecord record = new MqMsgSendFailRecord();
            record.setState("0");
            record.setMsgContent(JSON.toJSONString(event));
            record.setSendTime(new Date());
            boolean saved = recordService.save(record);
            if (!saved) {
                log.error("mq msg send fail and record to db fail");
            }
        }
    }


}
