package marketing.lottery.rpc;

import marketing.lottery.rpc.dto.ActionResult;
import marketing.lottery.rpc.req.LotteryDrawReq;

/**
 * 抽奖接口
 */
public interface ILotteryRpcService {

    boolean prepare(Long activityId);

    ActionResult draw(LotteryDrawReq req);

}
