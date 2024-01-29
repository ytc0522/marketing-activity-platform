package marketing.activity.lottery.rpc.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 中奖的奖品
 */
@Data
public class WinAward implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 活动ID
     */
    private Long activityId;

    /**
     * 奖品ID
     */
    private String awardId;


    /**
     * 奖品名称
     */
    private String awardName;


}
