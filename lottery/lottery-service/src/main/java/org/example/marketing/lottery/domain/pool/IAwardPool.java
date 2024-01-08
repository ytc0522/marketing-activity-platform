package org.example.marketing.lottery.domain.pool;

import org.example.marketing.lottery.rpc.dto.AwardInfo;
import org.example.marketing.lottery.rpc.dto.LotteryRich;

/**
 * 奖品池
 */
public interface IAwardPool {


    /**
     * 初始化奖品池
     */
    void initPool(LotteryRich lotteryRich);

    /**
     * 抽出一个奖品
     * @return 奖品ID
     */
    String doDraw(Long lotteryId);


}
