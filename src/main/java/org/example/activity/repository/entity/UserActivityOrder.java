package org.example.activity.repository.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 * @TableName user_activity_order_0
 */
@TableName(value ="user_activity_order_0")
@Data
public class UserActivityOrder implements Serializable {
    /**
     * 
     */
    private Long id;

    /**
     * 
     */
    private String orderId;

    /**
     * 商户ID
     */
    private Long merchantId;

    /**
     * 
     */
    private String orderStatus;

    /**
     * 
     */
    private Long activityId;

    /**
     * 
     */
    private String activityType;

    /**
     * 
     */
    private Date grantDate;

    /**
     * 
     */
    private String grantType;

    /**
     * 
     */
    private Long strategyId;

    /**
     * 
     */
    private Long awardId;

    /**
     * 
     */
    private String awardName;

    /**
     * 
     */
    private String awardType;

    /**
     * 
     */
    private String awardContent;

    /**
     * 
     */
    private String createEventSendState;

    /**
     * 
     */
    private Date createTime;

    /**
     * 
     */
    private Date updateTime;

    /**
     * 
     */
    private String userId;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}