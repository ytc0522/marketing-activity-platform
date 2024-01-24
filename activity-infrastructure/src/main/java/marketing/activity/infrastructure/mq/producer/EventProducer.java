package marketing.activity.infrastructure.mq.producer;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import marketing.activity.infrastructure.event.Event;
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

//    @Resource
//    private MqMsgSendFailRecordMapper recordMapper;

    public boolean publish(Event event) {
        boolean published = false;
        try {
            String msg = JSON.toJSONString(event);
            Message<String> message = MessageBuilder.withPayload(msg).build();
            log.info("【事件发布-{}】{}", event.getType(), msg);
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
//    public void publishWithRecord(Event event) {
//        if (!publish(event)) {
//            MqMsgSendFailRecord record = new MqMsgSendFailRecord();
//            record.setState("0");
//            record.setMsgContent(JSON.toJSONString(event));
//            record.setSendTime(new Date());
//            if (!(recordMapper.insert(record) > 0)) {
//                // 如果走到了这里，消息没有发出去，又没有记录下来，这就相当于丢了，得记录下来。
//                log.error("mq msg send failed and record to db failed:{}", JSON.toJSONString(event));
//            }
//        }
//    }


}
