package marketing.activity.lottery.order.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class LotteryOrderDto implements Serializable {


    private static final long serialVersionUID = 4595130659943625896L;
    private Long id;

    /**
     *
     */
    private String orderId;

    /**
     * 用户ID
     */
    private String userId;

    /**
     * 商户ID
     */
    private String merchantId;

    /**
     * uuid 用来防止创建重复订单
     */
    private String uuid;

    /**
     * 订单状态
     * 0: 未发放
     * 1: 已发放
     */
    private Integer orderStatus;

    /**
     * 活动ID
     */
    private Long activityId;


    /**
     * 奖品ID
     */
    private String awardId;

    /**
     * 奖品名称
     */
    private String awardName;


    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

}
