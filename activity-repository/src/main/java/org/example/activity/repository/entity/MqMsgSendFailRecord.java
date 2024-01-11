package org.example.activity.repository.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * MQ消息发送失败记录表
 *
 * @TableName mq_msg_send_fail_record
 */
@TableName(value = "mq_msg_send_fail_record")
@Data
public class MqMsgSendFailRecord implements Serializable {
    /**
     *
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 状态，0:未发送成功 1:已发送成功
     */
    private String state;

    /**
     * 消息内容
     */
    private String msgContent;

    /**
     * 失败原因，异常信息等
     */
    private String failReason;

    /**
     * 发送时间
     */
    private Date sendTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}