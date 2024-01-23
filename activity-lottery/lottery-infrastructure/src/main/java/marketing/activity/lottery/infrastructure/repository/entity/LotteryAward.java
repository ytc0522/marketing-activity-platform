package marketing.activity.lottery.infrastructure.repository.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 抽奖奖品
 *
 * @TableName status
 */
@TableName(value = "lottery_award")
@Data
public class LotteryAward implements Serializable {
    /**
     * 自增ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;


    private String activityId;

    /**
     * 奖品ID
     */
    private String awardId;

    /**
     * 奖品描述
     */
    private String awardName;

    /**
     * 奖品库存
     */
    private Integer initCount;

    /**
     * 奖品剩余库存
     */
    private Integer availableCount;

    /**
     * 中奖概率
     */
    private BigDecimal probability;

    /**
     * 版本
     */
    private int version;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改时间
     */
    private Date updateTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

}