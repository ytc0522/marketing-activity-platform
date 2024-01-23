package marketing.activity.lottery.rpc;

import cn.hutool.core.lang.Snowflake;
import com.alibaba.dubbo.config.annotation.Service;
import lombok.extern.slf4j.Slf4j;
import marketing.activity.base.rpc.IActivityBaseRpc;
import marketing.activity.base.rpc.model.RequirementsReqDto;
import marketing.activity.base.rpc.model.RequirementsResultDto;
import marketing.activity.lottery.domain.pool.ILotteryAwardPool;
import marketing.activity.lottery.domain.stock.ILotteryAwardStockStorage;
import marketing.activity.lottery.order.ILotteryOrderRpcService;
import marketing.activity.lottery.order.dto.LotteryWinAwardDto;
import marketing.activity.lottery.service.LotteryActivityService;
import marketing.lottery.rpc.ILotteryRpcService;
import marketing.lottery.rpc.constants.Constants;
import marketing.lottery.rpc.dto.ActionResult;
import marketing.lottery.rpc.dto.LotteryActivityDto;
import marketing.lottery.rpc.dto.LotteryActivityRich;
import marketing.lottery.rpc.dto.LotteryAwardDto;
import marketing.lottery.rpc.req.LotteryDrawReq;
import org.apache.dubbo.config.annotation.DubboReference;

import javax.annotation.Resource;
import java.util.List;
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

        LotteryWinAwardDto lotteryAward = new LotteryWinAwardDto();

        // 先判断用户是否具备参加活动的资格
        RequirementsReqDto requirementsReq = RequirementsReqDto.builder()
                .activityId(req.getActivityId())
                .userId(req.getUserId())
                .build();
        RequirementsResultDto result = baseRpc.check(requirementsReq);
        if (!result.isOk()) {
            log.warn("当前你还不具备条件：{}", result.getReason());
            return ActionResult.failure(result.getReason());
        }

        // 从缓存中获取 抽奖相关信息
        LotteryActivityRich lotteryActivityRich = lotteryActivityService.getFromCache(req.getActivityId());

        LotteryActivityDto lotteryActivity = lotteryActivityRich.getLotteryActivity();

        Long activityId = lotteryActivity.getActivityId();

        Integer probabilityType = lotteryActivity.getProbabilityType();
        if (probabilityType == null) probabilityType = 0;

        ILotteryAwardPool lotteryAwardPool = lotteryAwardPoolMap.get(probabilityType);

        // 计算抽奖结果，拿到奖项ID
        String awardId = lotteryAwardPool.doDraw(activityId);

        if (Constants.NULL.equals(awardId)) {
            return ActionResult.failure();
        }

        // 扣减库存
        boolean success = stockStorage.deductStock(activityId, awardId);

        if (!success) {
            // 扣减库存失败,表示未中奖,同时也表明该奖品库存没了
            // 如果是 动态概率，要将奖品从奖品池中删除
            lotteryAwardPool.removeAward(activityId, awardId);
            return ActionResult.failure();
        }

        List<LotteryAwardDto> awardDtos = lotteryActivityRich.getLotteryAwardlDtoList();
        LotteryAwardDto lotteryAwardDto = awardDtos.stream().filter((e) -> e.getAwardId().equals(awardId)).findFirst().get();

        lotteryAward.setActivityId(req.getActivityId());
        lotteryAward.setUserId(req.getUserId());
        lotteryAward.setAwardId(awardId);
        lotteryAward.setActivityName(lotteryActivity.getActivityName());
        lotteryAward.setAwardName(lotteryAwardDto.getAwardName());

        try {
            lotteryOrderRpcService.createOrder(lotteryAward, snowflake.nextIdStr());
        } catch (Exception e) {
            log.error("创建订单失败", e);
            // 如果创建订单失败，需要回滚库存,表示 未中奖
            stockStorage.rollBack(activityId, awardId);
            return ActionResult.failure();

        }
        // 表示用户已经抽到奖品了
        log.info("【中奖了】用户：{} ,LotteryId:{}，奖品：{}", req.getUserId(), activityId, awardId);
        return ActionResult.success(lotteryAward);
    }
}
