package org.example.marketing.common.mq;

import lombok.Data;

import java.util.UUID;

@Data
public class Event {


    private String eventId;

    private Type type;

    private Object body;

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

    }


}