package org.example.marketing.lottery.domain.pool;

/**
 * 奖品库存
 */
public interface IAwardStock {

    /**
     * 扣减库存，这里要具备原子性、数据一致性。
     *
     * @return
     */
    public boolean deductStock(Long lotteryId, String awardId);



}
