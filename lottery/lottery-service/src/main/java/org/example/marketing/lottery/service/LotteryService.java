package org.example.marketing.lottery.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.example.marketing.common.ActionResult;
import org.example.marketing.lottery.repository.entity.Lottery;
import org.example.marketing.lottery.rpc.dto.AwardInfo;
import org.example.marketing.lottery.rpc.dto.LotteryRich;
import org.example.marketing.lottery.rpc.req.DrawReq;

/**
 * @author jack
 * @description 针对表【lottery】的数据库操作Service
 * @createDate 2024-01-07 20:39:51
 */
public interface LotteryService extends IService<Lottery> {


    LotteryRich getFromCache(Long lotteryId);

    /**
     * 扣减库存
     */
    boolean deductStock(Long lotteryId, String awardId);

}
