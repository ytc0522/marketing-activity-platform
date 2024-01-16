package org.example.marketing.lottery.repository.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @TableName lottery_activity
 */
@TableName(value = "lottery_activity")
@Data
public class LotteryActivity implements Serializable {
    /**
     * 
     */
    @TableId
    private Long id;


    private Long activityId;

    private String activityName;

    private String activityDesc;


    private Long merchantId;

    private Date beginTime;

    private Date endTime;

    /**
     * 0: 下线
     * 1: 上线
     */
    private Integer status;

    /**
     * 概率类型：0-单项不变概率，1-剩余变化概率
     */
    private Integer probabilityType;

    /**
     * 创建人ID
     */
    private String creator;

    /**
     * 
     */
    private Date createTime;

    private Date updateTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;


}