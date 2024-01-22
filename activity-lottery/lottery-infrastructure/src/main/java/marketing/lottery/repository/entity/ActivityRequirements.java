package marketing.lottery.repository.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @TableName activity_requirements
 */
@TableName(value = "activity_requirements")
@Data
public class ActivityRequirements implements Serializable {

    /**
     *
     */
    private Integer id;

    private Long activityId;

    /**
     *
     */
    private String ruleKey;

    /**
     *
     */
    private Integer ruleType;

    /**
     *
     */
    private Date createTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}