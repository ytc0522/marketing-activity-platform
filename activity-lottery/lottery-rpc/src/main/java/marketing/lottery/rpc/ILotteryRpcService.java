package marketing.lottery.rpc;

import marketing.activity.lottery.order.dto.LotteryWinAwardDto;
import marketing.lottery.rpc.req.LotteryDrawReq;

/**
 * 抽奖接口
 */
public interface ILotteryRpcService {

    boolean prepare(Long activityId);

    LotteryWinAwardDto draw(LotteryDrawReq req);

}