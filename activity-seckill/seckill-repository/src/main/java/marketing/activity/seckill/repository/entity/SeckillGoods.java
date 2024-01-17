package marketing.activity.seckill.repository.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @TableName seckill_goods
 */
@TableName(value = "seckill_goods")
@Data
public class SeckillGoods implements Serializable {
    /**
     *
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     *
     */
    private Long activityId;

    /**
     *
     */
    private String goodsId;

    /**
     *
     */
    private String goodsName;

    /**
     *
     */
    private Integer initCount;

    /**
     *
     */
    private Integer availableCount;

    /**
     *
     */
    private Integer version;

    /**
     *
     */
    private Date createTime;

    /**
     *
     */
    private Date updateTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}