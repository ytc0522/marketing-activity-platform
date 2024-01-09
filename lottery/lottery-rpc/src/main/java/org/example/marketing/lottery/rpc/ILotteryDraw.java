package org.example.marketing.lottery.rpc;

import org.example.marketing.common.ActionResult;
import org.example.marketing.lottery.rpc.dto.AwardInfo;
import org.example.marketing.lottery.rpc.req.DrawReq;

/**
 * 抽奖接口
 */
public interface ILotteryDraw {

    ActionResult prepare(Long lotteryId);

    ActionResult<AwardInfo> draw(DrawReq req);

}
