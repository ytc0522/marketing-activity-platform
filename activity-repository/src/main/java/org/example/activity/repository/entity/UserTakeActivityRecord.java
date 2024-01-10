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
 * @TableName user_take_activity_record
 */
@TableName(value ="user_take_activity_record")
@Data
public class UserTakeActivityRecord implements Serializable {
    /**
     * 
     */
    @TableId
    private Long id;

    /**
     * 
     */
    private String userId;

    /**
     * 
     */
    private Long activityId;

    /**
     * 0: 领取活动未完成
     * 1: 领取活动已完成
     */
    private String state;

    /**
     * 参与时间
     */
    private Date takeTime;

    /**
     * 创建时间
     */
    private Date createTime;

    private Date updateTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;


}