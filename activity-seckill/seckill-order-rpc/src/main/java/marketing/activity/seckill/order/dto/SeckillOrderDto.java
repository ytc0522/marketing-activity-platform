package marketing.activity.seckill.order.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class SeckillOrderDto implements Serializable {

    private static final long serialVersionUID = -1136002861866558992L;

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

}
