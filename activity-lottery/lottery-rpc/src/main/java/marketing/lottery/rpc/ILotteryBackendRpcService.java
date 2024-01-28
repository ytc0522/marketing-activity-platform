package marketing.lottery.rpc;

import marketing.lottery.rpc.dto.LotteryActivityDto;
import marketing.lottery.rpc.dto.PageData;
import marketing.lottery.rpc.req.ActivityQueryReq;

public interface ILotteryBackendRpcService {

    public PageData<LotteryActivityDto> queryPage(ActivityQueryReq req);


}
