package marketing.activity.seckill.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class SeckillReq implements Serializable {

    private static final long serialVersionUID = 8458864270464280376L;
    private Long activityId;

    private String goodsId;

    private String userId;

}
