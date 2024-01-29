package marketing.activity.lottery.rpc;

import marketing.activity.lottery.rpc.dto.ActionResult;
import marketing.activity.lottery.rpc.req.LotteryDrawReq;

/**
 * 抽奖接口
 */
public interface ILotteryRpcService {

    boolean prepare(Long activityId);

    ActionResult draw(LotteryDrawReq req);

}
