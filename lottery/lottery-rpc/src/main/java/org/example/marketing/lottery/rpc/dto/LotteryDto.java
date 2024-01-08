package org.example.marketing.lottery.rpc.dto;

import lombok.Data;

import java.util.Date;

@Data
public class LotteryDto {

    private Long id;

    /**
     * 概率类型：0-单项不变概率，1-剩余变化概率
     */
    private Integer chanceType;

    /**
     * 发放奖励类型：0:立即发放，1-结束后发放，2-人工发放
     */
    private Integer grantType;

    /**
     *
     */
    private Date createTime;

    private static final long serialVersionUID = 1L;

}
