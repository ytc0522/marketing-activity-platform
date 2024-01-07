package org.example.marketing.lottery.rpc.req;

import java.io.Serializable;

public class DrawReq implements Serializable {

    private String userId;

    private Long activityId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Long getActivityId() {
        return activityId;
    }

    public void setActivityId(Long activityId) {
        this.activityId = activityId;
    }
}
