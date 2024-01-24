package marketing.activity.infrastructure.event;

import lombok.Data;
import marketing.activity.infrastructure.util.SnowflakeUtil;

@Data
public class Event<T> {


    private String eventId;

    private Type type;

    private T body;

    public Event() {
        this.eventId = SnowflakeUtil.nextIdStr();
    }


    public enum Type {

        /**
         * 用户参加活动日志
         */
        USER_TAKE_ACTIVITY,

        /**
         * 用户完成活动
         */
        USER_FINISH_ACTIVITY,

        /**
         * 活动订单已创建
         */
        ACTIVITY_ORDER_CREATED,

        /**
         * 用户获得奖品
         */
        USER_WIN_AWARD,

        /**
         * 用户在秒杀活动中抢到了商品
         */
        SECKILL_USER_WIN_GOODS

    }


}