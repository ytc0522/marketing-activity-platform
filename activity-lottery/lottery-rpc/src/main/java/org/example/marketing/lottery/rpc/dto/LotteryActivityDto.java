package org.example.marketing.lottery.rpc.dto;

import lombok.Data;

import java.util.Date;

@Data
public class LotteryActivityDto {

    private Long id;

    private Long activityId;

    private String activityName;

    private String activityDesc;

    private Date beginTime;

    private Date endTime;

    /**
     * 0: 下线
     * 1: 上线
     */
    private Integer status;

    /**
     * 概率类型：0-单项不变概率，1-剩余变化概率
     */
    private Integer probabilityType;

    /**
     * 创建人ID
     */
    private String creator;

    /**
     *
     */
    private Date createTime;

    private Date updateTime;


    private static final long serialVersionUID = 1L;

}
