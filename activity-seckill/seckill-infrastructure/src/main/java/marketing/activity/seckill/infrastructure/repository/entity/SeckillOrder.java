package marketing.activity.seckill.infrastructure.repository.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @TableName seckill_order
 */
@TableName(value = "seckill_order")
@Data
public class SeckillOrder implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 
     */
    private String userId;

    /**
     * 
     */
    private String orderId;

    /**
     * 
     */
    private Long merchantId;

    /**
     * 订单状态 0:刚提交 2:已付款 
     */
    private String orderStatus;

    /**
     * 
     */
    private Long activityId;

    /**
     * 
     */
    private Long goodsId;

    /**
     * 
     */
    private String goodsName;

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
    private String uuid;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}