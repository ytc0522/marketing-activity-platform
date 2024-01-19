package marketing.activity.infrastructure.event;

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
         * 用户抢到了商品
         */
        SECKILL_USER_WIN_GOODS

    }


}