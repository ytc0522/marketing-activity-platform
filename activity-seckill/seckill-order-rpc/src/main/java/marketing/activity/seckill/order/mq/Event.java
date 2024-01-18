package marketing.activity.seckill.order.mq;

import lombok.Data;

import java.util.UUID;

@Data
public class Event<T> {


    private String eventId;

    private Type type;

    private T body;

    public Event() {
        this.eventId = UUID.randomUUID().toString();
    }


    public enum Type {

        /**
         * 活动订单已创建
         */
        ACTIVITY_ORDER_CREATED,

        /**
         * 用户中奖
         */
        USER_WIN_AWARD,


        SECKILL_USER_WIN_GOODS

    }


}