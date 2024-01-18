package marketing.activity.seckill.order.dto;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class SeckillOrderCreateReq implements Serializable {

    private static final long serialVersionUID = -8140675158190637140L;

    private String userId;

    private Long activityId;

    private String goodsId;

    private String orderId;
}