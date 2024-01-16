package org.example.marketing.activity.order.repository.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @TableName user_activity_order
 */
@TableName(value = "lottery_order")
@Data
public class LotteryOrder implements Serializable {
    /**
     *
     */
    @TableId
    private Long id;

    /**
     *
     */
    private String orderId;

    /**
     * 用户ID
     */
    private String userId;

    /**
     * 商户ID
     */
    private String merchantId;

    /**
     * uuid 用来防止创建重复订单
     */
    private String uuid;

    /**
     * 订单状态
     * 0: 未发放
     * 1: 已发放
     */
    private Integer status;

    /**
     * 活动ID
     */
    private Long activityId;


    /**
     * 奖品ID
     */
    private String awardId;

    /**
     * 奖品名称
     */
    private String awardName;


    /**
     * 创建订单事件发送状态 0:未发送 1:已发送 2:发送失败
     */
    private String createEventSendState;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

}