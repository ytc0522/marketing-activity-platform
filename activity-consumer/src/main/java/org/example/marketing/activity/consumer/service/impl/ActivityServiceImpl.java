package org.example.marketing.activity.consumer.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.example.activity.repository.entity.Activity;
import org.example.activity.repository.entity.UserActivityOrder;
import org.example.activity.repository.mapper.ActivityMapper;
import org.example.marketing.activity.consumer.service.UserActivityOrderService;
import org.example.marketing.activity.consumer.service.ActivityService;
import org.example.marketing.activity.consumer.service.AwardService;
import org.example.marketing.activity.consumer.utils.SnowFlakeUtil;
import org.example.marketing.common.ActionResult;
import org.example.marketing.common.enums.ActivityType;
import org.example.marketing.common.req.activity.TakeActivityReq;
import org.example.marketing.lottery.rpc.ILotteryDraw;
import org.example.marketing.lottery.rpc.dto.WinAward;
import org.example.marketing.lottery.rpc.req.DrawReq;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.UUID;

/**
 * @author jack
 * @description 针对表【activity(活动配置)】的数据库操作Service实现
 * @createDate 2024-01-07 01:13:48
 */
@Slf4j
@Service
public class ActivityServiceImpl extends ServiceImpl<ActivityMapper, Activity>
        implements ActivityService {

    @DubboReference
    private ILotteryDraw lotteryDraw;

    @Resource
    private AwardService awardService;

    @Resource
    private UserActivityOrderService userActivityOrderService;

    @Resource
    private SnowFlakeUtil snowFlakeUtil;


    @Override
    public ActionResult takeActivity(TakeActivityReq req) {

        Long activityId = req.getActivityId();
        Activity activity = this.lambdaQuery().eq(Activity::getActivityId, activityId).one();

        if (activity == null) {
            return ActionResult.failure("活动不存在");
        }

        // 校验活动时间
        // 校验用户活动参与次数
        // 风控-校验用户IP、用户ID是否在黑名单中。


        // 活动类型
        ActionResult result = ActionResult.success();

        WinAward winAward = null;
        String activityType = activity.getActivityType();
        if (ActivityType.LOTTERY.toString().equals(activityType)) {
            DrawReq drawReq = new DrawReq();
            drawReq.setUserId(req.getUserId());
            drawReq.setLotteryId(activity.getStrategyId());
            ActionResult<WinAward> lotteryResult = lotteryDraw.draw(drawReq);
            // 未中奖
            winAward = lotteryResult.getValue();
            if (!lotteryResult.isSuccess() || winAward == null || winAward.getAwardId() == null) {
                return result;
            }
        }

        // 中奖了
        // 记录中奖记录，生成活动订单
        UserActivityOrder userActivityOrder = new UserActivityOrder();
        userActivityOrder.setOrderStatus("1");
        // 活动信息
        userActivityOrder.setActivityId(activityId);
        userActivityOrder.setActivityType(activityType);
        userActivityOrder.setStrategyId(activity.getStrategyId());

        // OrderId 采用雪花算法生成
        userActivityOrder.setOrderId(String.valueOf(snowFlakeUtil.snowflakeId()));

        // 奖品信息
        userActivityOrder.setAwardId(winAward.getAwardId());
        userActivityOrder.setAwardName(winAward.getAwardName());
        userActivityOrder.setAwardContent(winAward.getAwardContent());
        userActivityOrder.setAwardType(winAward.getAwardType());
        userActivityOrder.setGrantType(winAward.getGrantType());
        userActivityOrder.setCreateTime(new Date());
        userActivityOrder.setUpdateTime(new Date());

        try {
            boolean saved = userActivityOrderService.save(userActivityOrder);
            if (!saved) {
                log.error("保存活动订单失败,订单：{}", JSON.toJSONString(userActivityOrder));
            }
        } catch (Exception e) {
            log.error("保存活动订单失败,订单：{}", JSON.toJSONString(userActivityOrder), e);
        }


        return ActionResult.success(winAward);
    }
}




