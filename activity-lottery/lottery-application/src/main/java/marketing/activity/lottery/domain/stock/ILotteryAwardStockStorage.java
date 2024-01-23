package marketing.activity.lottery.domain.stock;

/**
 * 抽奖奖品库存存储
 */
public interface ILotteryAwardStockStorage {


    /**
     * 刷新抽奖活动的库存
     *
     * @return
     */
    void refreshStock(Long activityId);

    /**
     * 扣减库存，这里要具备原子性、数据一致性。
     *
     * @return
     */
    boolean deductStock(Long activityId, String awardId);


    /**
     * 回滚库存
     *
     * @param activityId
     * @param awardId
     * @return
     */
    boolean rollBack(Long activityId, String awardId);


}
