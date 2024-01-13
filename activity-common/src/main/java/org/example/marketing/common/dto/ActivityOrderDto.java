package org.example.marketing.common.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class ActivityOrderDto implements Serializable {

    private static final long serialVersionUID = 1L;


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
     * 订单状态
     */
    private String orderStatus;

    /**
     * 活动ID
     */
    private Long activityId;

    /**
     * 活动类型
     */
    private String activityType;

    /**
     * 发奖时间
     */
    private Date grantDate;

    /**
     * 发放奖品方式（1:即时、2:定时[含活动结束]、3:人工）
     */
    private String grantType;

    /**
     * 活动策略ID
     */
    private Long strategyId;

    /**
     * 奖品ID
     */
    private String awardId;

    /**
     * 奖品名称
     */
    private String awardName;

    /**
     * 奖品类型
     */
    private String awardType;

    /**
     * 奖品内容
     */
    private String awardContent;

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


}
