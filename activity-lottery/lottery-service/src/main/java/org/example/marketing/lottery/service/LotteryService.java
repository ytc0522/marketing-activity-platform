package org.example.marketing.lottery.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.example.marketing.lottery.repository.entity.Lottery;
import org.example.marketing.lottery.rpc.dto.LotteryRich;

/**
 * @author jack
 * @description 针对表【lottery】的数据库操作Service
 * @createDate 2024-01-07 20:39:51
 */
public interface LotteryService extends IService<Lottery> {


    LotteryRich refreshCache(Long lotteryId);

    LotteryRich getFromCache(Long lotteryId);

    /**
     * 扣减库存
     * 是否需要幂等性扣减
     */
    boolean deductStock(Long lotteryId, String awardId);


    boolean updateStockCount(Long lotteryId, String awardId,Long surplusCount);

}
