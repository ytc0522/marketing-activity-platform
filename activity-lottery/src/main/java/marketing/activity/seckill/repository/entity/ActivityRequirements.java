package marketing.activity.seckill.repository.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 * @TableName activity_requirements
 */
@TableName(value ="activity_requirements")
@Data
public class ActivityRequirements implements Serializable {
    /**
     * 
     */
    private Integer id;

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