package org.example.marketing.lottery.rpc.req;

import lombok.Data;

import java.io.Serializable;

@Data
public class DrawReq implements Serializable {

    private String userId;

    private String activityId;

    private Long lotteryId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Long getLotteryId() {
        return lotteryId;
    }

    public void setLotteryId(Long lotteryId) {
        this.lotteryId = lotteryId;
    }
}
