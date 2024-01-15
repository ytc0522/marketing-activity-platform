package org.example.marketing.common.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserWinAwardDto implements Serializable {


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
     * 活动类型：1-抽奖 2-抢红包
     */
    private String activityType;

    /**
     * 活动策略ID
     */
    private Long strategyId;

    /**
     * 奖品ID
     */
    private String awardId;

    /**
     * 奖品类型（1:文字描述、2:兑换码、3:优惠券、4:实物奖品）
     */
    private String awardType;

    /**
     * 奖品名称
     */
    private String awardName;

    /**
     * 奖品内容「描述、奖品码、sku」
     */
    private String awardContent;

    /**
     * 发放奖品方式（1:即时、2:定时[含活动结束]、3:人工）
     */
    private String grantType;


}
