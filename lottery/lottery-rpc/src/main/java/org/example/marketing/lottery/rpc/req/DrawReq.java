package org.example.marketing.lottery.rpc.req;

import java.io.Serializable;

public class DrawReq implements Serializable {

    private String userId;

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
