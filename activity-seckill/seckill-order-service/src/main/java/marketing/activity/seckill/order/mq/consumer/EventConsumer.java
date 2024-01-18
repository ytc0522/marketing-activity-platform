package marketing.activity.seckill.order.mq.consumer;

import com.alibaba.fastjson.JSON;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import marketing.activity.seckill.order.event.EventProcess;
import marketing.activity.seckill.order.mq.Event;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;

@Slf4j
@Component
@EnableBinding(Sink.class)
public class EventConsumer {


    @Resource
    private EventProcess eventProcess;

    @EventListener
    @StreamListener(Sink.INPUT)
    public void listen(Message<Event> message) {
        Channel channel = (Channel) message.getHeaders().get(AmqpHeaders.CHANNEL);
        Long deliveryTag = (Long) message.getHeaders().get(AmqpHeaders.DELIVERY_TAG);
        Event event = message.getPayload();
        log.info("【监听事件-{}】{}", event.getType(), JSON.toJSONString(event));
        try {
            eventProcess.process(event);
            channel.basicAck(deliveryTag, false);
        } catch (Exception e) {
            log.error("【消费者异常信息】", e);
            try {
                if (channel != null) {
                    channel.basicReject(deliveryTag, true); // 让消息变为ready状态,当requeue=false,会丢弃消息
                }
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            throw new RuntimeException(e);
        }

    }


}
