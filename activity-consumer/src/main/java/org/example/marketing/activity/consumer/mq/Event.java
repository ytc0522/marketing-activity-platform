package org.example.marketing.activity.consumer.mq;

import cn.hutool.core.util.IdUtil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class Event {


    private String eventId;
    /**
     * 延迟时间
     */
    private Long delayTime;

    private Type type;

    private Object body;

    public Event() {
        this.eventId = IdUtil.fastSimpleUUID();
    }

    public boolean isDelayConsume() {
        return this.delayTime != null && this.delayTime > 0;
    }


    public enum Type {
        ACTIVITY_ORDER_CREATE

    }


}