package marketing.activity.seckill.order.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class SeckillWinGoods implements Serializable {

    private static final long serialVersionUID = 8873479023867231930L;
    private Long activityId;

    private String goodsId;

    private String userId;

    // 可用库存
    private Integer availableStock;

    //
    private String orderId;

}
