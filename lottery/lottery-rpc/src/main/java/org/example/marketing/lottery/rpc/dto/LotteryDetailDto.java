package org.example.marketing.lottery.rpc.dto;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class LotteryDetailDto implements Serializable {

    private Long id;

    /**
     * 抽奖ID
     */
    private String lotteryId;

    /**
     * 奖品ID
     */
    private String awardId;

    /**
     * 奖品描述
     */
    private String awardName;

    /**
     * 奖品类型
     */
    private String awardType;

    /**
     * 奖品库存
     */
    private Integer awardCount;

    /**
     * 奖品内容
     */
    private String awardContent;

    /**
     * 奖品剩余库存
     */
    private Integer awardSurplusCount;

    /**
     * 中奖概率
     */
    private BigDecimal awardRate;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改时间
     */
    private Date updateTime;

    private static final long serialVersionUID = 1L;


}
