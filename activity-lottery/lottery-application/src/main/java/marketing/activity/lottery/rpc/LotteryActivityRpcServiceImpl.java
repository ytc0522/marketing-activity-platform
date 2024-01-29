package marketing.activity.lottery.rpc;

import cn.hutool.core.lang.Snowflake;
import com.alibaba.dubbo.config.annotation.Service;
import lombok.extern.slf4j.Slf4j;
import marketing.activity.base.rpc.IActivityBaseRpc;
import marketing.activity.base.rpc.model.RequirementsReqDto;
import marketing.activity.base.rpc.model.RequirementsResultDto;
import marketing.activity.infrastructure.event.UserEventHelper;
import marketing.activity.infrastructure.event.body.UserActivityLogEventBody;
import marketing.activity.lottery.domain.pool.ILotteryAwardPool;
import marketing.activity.lottery.domain.stock.ILotteryAwardStockStorage;
import marketing.activity.lottery.order.ILotteryOrderRpcService;
import marketing.activity.lottery.rpc.dto.ActionResult;
import marketing.activity.lottery.rpc.dto.LotteryActivityRich;
import marketing.activity.lottery.rpc.req.LotteryDrawReq;
import marketing.activity.lottery.service.LotteryActivityService;
import org.apache.dubbo.config.annotation.DubboReference;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Map;

@Slf4j
@Service
public class LotteryActivityRpcServiceImpl implements ILotteryRpcService {


    @Resource
    private LotteryActivityService lotteryActivityService;

    @Resource
    private Map<Integer, ILotteryAwardPool> lotteryAwardPoolMap;

    @DubboReference
    private ILotteryOrderRpcService lotteryOrderRpcService;

    @Resource
    private ILotteryAwardStockStorage stockStorage;

    private Snowflake snowflake = new Snowflake();

    @DubboReference
    private IActivityBaseRpc baseRpc;

    @Resource
    private UserEventHelper userEventHelper;


    @Override
    public boolean prepare(Long activityId) {
        LotteryActivityRich lotteryActivityRich = lotteryActivityService.refreshCache(activityId);
        ILotteryAwardPool lotteryAwardPool = lotteryAwardPoolMap.get(lotteryActivityRich.getLotteryActivity().getProbabilityType());
        lotteryAwardPool.refreshPool(lotteryActivityRich);
        stockStorage.refreshStock(activityId);
        return true;
    }


    @Override
    public ActionResult draw(LotteryDrawReq req) {
        // 先判断用户是否具备参加活动的资格
        RequirementsReqDto requirementsReq = RequirementsReqDto.builder()
                .activityId(req.getActivityId())
                .userId(req.getUserId())
                .build();
        RequirementsResultDto checkResult = baseRpc.check(requirementsReq);
        if (!checkResult.isOk()) {
            log.warn("当前你还不具备条件：{}", checkResult.getReason());
            return ActionResult.failure(checkResult.getReason());
        }

        // 发布用户参加活动的事件，写入用户日志
        UserActivityLogEventBody body = UserActivityLogEventBody.builder()
                .activityId(req.getActivityId())
                .userId(req.getUserId())
                .status(0)
                .date(new Date())
                .build();

        String eventId = userEventHelper.publishUserTakeActivityEvent(body);

        // 执行
        ActionResult result = lotteryActivityService.draw(req);

        // 发布用户参加活动消息，修改日志状态 省略 publishUserFinishActivity


        return result;
    }
}
