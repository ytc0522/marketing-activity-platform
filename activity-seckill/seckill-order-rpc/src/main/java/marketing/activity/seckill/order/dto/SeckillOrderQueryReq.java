package marketing.activity.seckill.order.dto;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Builder
@Data
public class SeckillOrderQueryReq implements Serializable {

    private static final long serialVersionUID = 5319488215135309033L;

    private String userId;

    private Long activityId;

    private Long goodsId;

    private String orderId;


}
