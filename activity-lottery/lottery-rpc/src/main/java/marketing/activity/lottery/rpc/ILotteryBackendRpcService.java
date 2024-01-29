package marketing.activity.lottery.rpc;

import marketing.activity.lottery.rpc.dto.LotteryActivityDto;
import marketing.activity.lottery.rpc.dto.PageData;
import marketing.activity.lottery.rpc.req.ActivityQueryReq;

public interface ILotteryBackendRpcService {

    public PageData<LotteryActivityDto> queryPage(ActivityQueryReq req);


}
