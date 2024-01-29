package marketing.activity.lottery.rpc.req;

import lombok.Data;

import java.io.Serializable;

@Data
public class LotteryDrawReq implements Serializable {

    private static final long serialVersionUID = -4304710515129039062L;
    private String userId;

    private Long activityId;


}
