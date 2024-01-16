package marketing.activity.lottery.order.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class LotteryWinAwardDto implements Serializable {


    private static final long serialVersionUID = -2071792317107340666L;


    private String userId;


    /**
     * 活动ID
     */
    private Long activityId;

    /**
     * 活动名称
     */
    private String activityName;


    /**
     * 奖品ID
     */
    private String awardId;

    /**
     * 奖品名称
     */
    private String awardName;


}
