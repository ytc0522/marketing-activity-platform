package org.example.marketing.activity.order.dto.req;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder()
public class ActivityOrderQueryReq extends PageReq {

    /**
     * 订单创建时间开始
     */
    private Date createTimeBegin;

    private Date createTimeEnd;

    private String userId;

    private String mchId;

    private Long activityId;

    private String activityType;

    private Long awardId;

    private String orderStatus;

    private String orderId;


}
