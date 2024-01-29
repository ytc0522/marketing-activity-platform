package marketing.activity.lottery.domain.pool;

import marketing.activity.lottery.rpc.dto.LotteryActivityRich;

/**
 * 奖品池
 *  只存放奖品和概率
 */
public interface ILotteryAwardPool {


    /**
     * 刷新奖品池
     */
    void refreshPool(LotteryActivityRich lotteryActivityRich);


    /**
     * 抽出一个奖品
     * @return 奖品ID
     */
    String doDraw(Long lotteryId);

    /**
     * 从抽奖奖品池中移除一个奖品
     */
    void removeAward(Long lotteryId, String awardId);


}
