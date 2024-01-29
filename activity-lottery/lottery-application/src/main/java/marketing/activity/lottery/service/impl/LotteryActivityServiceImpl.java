package marketing.activity.lottery.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.Snowflake;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import marketing.activity.base.rpc.IActivityBaseRpc;
import marketing.activity.base.rpc.model.RequirementsReqDto;
import marketing.activity.base.rpc.model.RequirementsResultDto;
import marketing.activity.infrastructure.event.UserEventHelper;
import marketing.activity.infrastructure.event.body.UserActivityLogEventBody;
import marketing.activity.infrastructure.util.RedisUtil;
import marketing.activity.lottery.domain.pool.ILotteryAwardPool;
import marketing.activity.lottery.domain.stock.ILotteryAwardStockStorage;
import marketing.activity.lottery.infrastructure.repository.entity.LotteryActivity;
import marketing.activity.lottery.infrastructure.repository.entity.LotteryAward;
import marketing.activity.lottery.infrastructure.repository.mapper.LotteryActivityMapper;
import marketing.activity.lottery.order.ILotteryOrderRpcService;
import marketing.activity.lottery.order.dto.LotteryWinAwardDto;
import marketing.activity.lottery.rpc.constants.Constants;
import marketing.activity.lottery.rpc.dto.ActionResult;
import marketing.activity.lottery.rpc.dto.LotteryActivityDto;
import marketing.activity.lottery.rpc.dto.LotteryActivityRich;
import marketing.activity.lottery.rpc.dto.LotteryAwardDto;
import marketing.activity.lottery.rpc.req.LotteryDrawReq;
import marketing.activity.lottery.service.LotteryActivityService;
import marketing.activity.lottery.service.LotteryAwardService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author jack
 * @description 针对表【lottery】的数据库操作Service实现
 * @createDate 2024-01-07 20:39:51
 */
@Slf4j
@Service
public class LotteryActivityServiceImpl extends ServiceImpl<LotteryActivityMapper, LotteryActivity>
        implements LotteryActivityService {


    @Resource
    private LotteryAwardService lotteryAwardService;

    @Resource
    private RedisUtil redisUtil;

    private static String LOTTERY_KEY = "LOTTERY_KEY:";

    @Resource
    private Map<Integer, ILotteryAwardPool> lotteryAwardPoolMap;

    @DubboReference
    private ILotteryOrderRpcService lotteryOrderRpcService;


    @Resource
    private LotteryActivityService lotteryActivityService;


    @Resource
    private ILotteryAwardStockStorage stockStorage;

    @Resource
    private Snowflake snowflake;

    @DubboReference
    private IActivityBaseRpc baseRpc;

    @Resource
    private UserEventHelper userEventHelper;


    /**
     * @param req
     * @return
     */
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
        LotteryActivityRich lotteryActivityRich = lotteryActivityService.query(req.getActivityId());
        // 省略活动日期的校验
        LotteryActivityDto lotteryActivity = lotteryActivityRich.getLotteryActivity();
        Long activityId = lotteryActivity.getActivityId();

        // 发布用户参加活动的事件，写入用户日志
        UserActivityLogEventBody body = UserActivityLogEventBody.builder()
                .activityId(activityId)
                .userId(req.getUserId())
                .status(0)
                .date(new Date())
                .build();

        String eventId = userEventHelper.publishUserTakeActivityEvent(body);

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


        // 发布用户参加活动消息，修改日志状态 省略 publishUserFinishActivity


        return ActionResult.success(lotteryAward);
    }

    @Override
    public LotteryActivityRich refreshCache(Long lotteryId) {
        String lotteryCacheKey = LOTTERY_KEY + lotteryId;
        redisUtil.del(lotteryCacheKey);
        return query(lotteryId);
    }

    @Override
    public LotteryActivityRich query(Long lotteryActivityId) {
        String lotteryCacheKey = LOTTERY_KEY + lotteryActivityId;

        LotteryActivityRich lotteryActivityRich = (LotteryActivityRich) redisUtil.get(lotteryCacheKey);

        if (lotteryActivityRich != null) {
            return lotteryActivityRich;
        }

        LotteryActivity lotteryActivity = this.lambdaQuery()
                .eq(LotteryActivity::getActivityId, lotteryActivityId)
                .last("limit 1")
                .one();
        if (lotteryActivity == null) {
            return null;
        }
        List<LotteryAward> detailList = lotteryAwardService.lambdaQuery().eq(LotteryAward::getActivityId, lotteryActivityId)
                .list();

        LotteryActivityRich newLotteryActivityRich = new LotteryActivityRich();
        LotteryActivityDto lotteryActivityDto = new LotteryActivityDto();
        BeanUtil.copyProperties(lotteryActivity, lotteryActivityDto);

        List<LotteryAwardDto> lotteryAwardDtos = BeanUtil.copyToList(detailList, LotteryAwardDto.class);

        newLotteryActivityRich.setLotteryActivity(lotteryActivityDto);
        newLotteryActivityRich.setLotteryAwardlDtoList(lotteryAwardDtos);

        redisUtil.set(lotteryCacheKey, newLotteryActivityRich, 3600 * 3);
        return newLotteryActivityRich;

    }
//
//    @Override
//    public boolean deductStock(Long lotteryActivityId, String awardId) {
//
//        UpdateWrapper<LotteryAward> updateWrapper = new UpdateWrapper<>();
//        updateWrapper.setSql("award_surplus_count = award_surplus_count - 1");
//        updateWrapper.eq("lottery_id", lotteryActivityId);
//        updateWrapper.eq("award_id", awardId);
//        updateWrapper.gt("award_surplus_count", 0);
//
//        return lotteryAwardService.update(updateWrapper);
//    }

//    @Override
//    public boolean updateStockCount(Long lotteryActivityId, String awardId, Long surplusCount) {
//
//        boolean update = lotteryAwardService.lambdaUpdate().eq(LotteryAward::getActivityId, lotteryActivityId)
//                .eq(LotteryAward::getAwardId, awardId)
//                .set(LotteryAward::getAvailableCount, surplusCount).update();
//        return update;
//    }


}




