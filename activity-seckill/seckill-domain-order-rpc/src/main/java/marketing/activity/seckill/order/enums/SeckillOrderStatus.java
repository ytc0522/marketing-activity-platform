package marketing.activity.seckill.order.enums;

public enum SeckillOrderStatus {

    SUBMITTED,
    /**
     * 待付款
     */
    NOT_PAY,

    /**
     * 超时未支付
     */
    OVER_TIME,

    /**
     * 待发货
     */
    WAIT_DELIVERED

}
