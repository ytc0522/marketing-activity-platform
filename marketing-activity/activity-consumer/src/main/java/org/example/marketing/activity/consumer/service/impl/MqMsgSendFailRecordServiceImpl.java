package org.example.marketing.activity.consumer.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.example.activity.repository.entity.MqMsgSendFailRecord;
import org.example.activity.repository.mapper.MqMsgSendFailRecordMapper;
import org.example.marketing.activity.consumer.service.MqMsgSendFailRecordService;
import org.springframework.stereotype.Service;

/**
 * @author jack
 * @description 针对表【mq_msg_send_fail_record(MQ消息发送失败记录表)】的数据库操作Service实现
 * @createDate 2024-01-11 15:12:21
 */
@Service
public class MqMsgSendFailRecordServiceImpl extends ServiceImpl<MqMsgSendFailRecordMapper, MqMsgSendFailRecord>
        implements MqMsgSendFailRecordService {

}




