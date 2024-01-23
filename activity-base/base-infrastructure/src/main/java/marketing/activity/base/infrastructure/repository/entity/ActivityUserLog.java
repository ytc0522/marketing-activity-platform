package marketing.activity.base.infrastructure.repository.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @TableName activity_user_log
 */
@TableName(value = "activity_user_log")
@Data
public class ActivityUserLog implements Serializable {
    /**
     *
     */
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
     * 活动类型
     */
    private String activityType;

    /**
     * 0:未完成 1:已完成
     */
    private Integer state;

    /**
     *
     */
    private Date takeTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}