package org.example.marketing.lottery.rpc;

import org.example.marketing.lottery.rpc.dto.WinAward;
import org.example.marketing.lottery.rpc.req.DrawReq;

/**
 * 抽奖接口
 */
public interface ILotteryDraw {

    boolean prepare(Long lotteryId);

    WinAward draw(DrawReq req);

}
