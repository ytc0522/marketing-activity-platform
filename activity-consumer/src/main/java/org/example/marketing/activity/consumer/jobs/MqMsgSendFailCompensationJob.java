package org.example.marketing.activity.consumer.jobs;

import com.alibaba.fastjson.JSONObject;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.annotation.XxlJob;
import com.xxl.job.core.log.XxlJobLogger;
import org.example.activity.repository.entity.MqMsgSendFailRecord;
import org.example.marketing.activity.consumer.mq.Event;
import org.example.marketing.activity.consumer.mq.producer.EventProducer;
import org.example.marketing.activity.consumer.service.MqMsgSendFailRecordService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * 事件发送失败 补偿任务
 */
@Component
public class MqMsgSendFailCompensationJob {

    @Resource
    private EventProducer eventProducer;

    @Resource
    private MqMsgSendFailRecordService mqMsgSendFailRecordService;


    /**
     * 每次从MQ发送消息失败的记录表中取出若干条发送失败的记录，然后重新发送给MQ。
     *
     * @param param
     * @return
     */
    @XxlJob(value = "compensation")
    public ReturnT<String> compensationHandler(String param) {
        XxlJobLogger.log("MQ消息补偿任务正在执行...");

        List<MqMsgSendFailRecord> failRecords = mqMsgSendFailRecordService.lambdaQuery()
                .eq(MqMsgSendFailRecord::getState, "0")
                .last("limit 100")
                .list();

        for (MqMsgSendFailRecord failRecord : failRecords) {
            String msgContent = failRecord.getMsgContent();
            Event event = JSONObject.parseObject(msgContent, Event.class);
            boolean published = eventProducer.publish(event);
            if (published) {
                failRecord.setState("1");
                failRecord.setUpdateTime(new Date());
                mqMsgSendFailRecordService.updateById(failRecord);
            }
        }
        XxlJobLogger.log("MQ消息补偿任务执行完成。");
        return ReturnT.SUCCESS;
    }

}
