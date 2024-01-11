package org.example.marketing.activity.consumer.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboReference;
import org.example.activity.repository.entity.Activity;
import org.example.activity.repository.entity.UserActivityOrder;
import org.example.activity.repository.entity.UserTakeActivityRecord;
import org.example.activity.repository.mapper.ActivityMapper;
import org.example.marketing.activity.consumer.mq.producer.EventProducer;
import org.example.marketing.activity.consumer.service.ActivityService;
import org.example.marketing.activity.consumer.service.AwardService;
import org.example.marketing.activity.consumer.service.UserActivityOrderService;
import org.example.marketing.activity.consumer.service.UserTakeActivityRecordService;
import org.example.marketing.common.ActionResult;
import org.example.marketing.common.dto.UserWinAwardDto;
import org.example.marketing.common.enums.ActivityType;
import org.example.marketing.common.mq.Event;
import org.example.marketing.common.req.activity.TakeActivityReq;
import org.example.marketing.lottery.rpc.ILotteryDraw;
import org.example.marketing.lottery.rpc.dto.WinAward;
import org.example.marketing.lottery.rpc.req.DrawReq;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;

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
    private UserTakeActivityRecordService userTakeActivityRecordService;


    @Resource
    private EventProducer eventProducer;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ActionResult takeActivity(TakeActivityReq req) {

        Long activityId = req.getActivityId();
        Activity activity = this.lambdaQuery().eq(Activity::getActivityId, activityId).one();

        if (activity == null) {
            return ActionResult.failure("活动不存在");
        }

        // 校验活动时间：校验活动时间
        // 校验用户活动参与次数： 查数据库比较
        // 风控-校验用户IP、用户ID是否在黑名单中

        // 插入活动领取记录表
        UserTakeActivityRecord record = new UserTakeActivityRecord();
        record.setActivityId(activityId);
        record.setUserId(req.getUserId());
        record.setState("0");
        record.setTakeTime(new Date());
        record.setCreateTime(new Date());
        record.setUpdateTime(new Date());

        userTakeActivityRecordService.save(record);


        // 活动类型
        ActionResult result = ActionResult.success();

        WinAward winAward = null;
        String activityType = activity.getActivityType();
        if (ActivityType.LOTTERY.toString().equals(activityType)) {
            DrawReq drawReq = new DrawReq();
            drawReq.setUserId(req.getUserId());
            drawReq.setLotteryId(activity.getStrategyId());
            ActionResult<WinAward> lotteryResult = lotteryDraw.draw(drawReq);
            winAward = lotteryResult.getValue();
        }

        // 更新领取活动记录表
        record.setState("1");
        userTakeActivityRecordService.updateById(record);

        // 增加用户参加的次数 更新用户活动次数表即可。
        //
        if (winAward == null || StringUtils.isEmpty(winAward.getAwardId())) {
            // 未中奖
            return result;
        }

        // 中奖了 保存中奖订单
        // 同步还是异步保存？这里可以通过MQ异步保存订单，然后在订单服务中发送订单已经创建的通知。
        publishWinAwardEvent(req.getUserId(), activity, winAward);

        // 保存订单 todo 不能在这里保存订单
        UserActivityOrder userActivityOrder = userActivityOrderService.saveWinAwardOrder(req.getUserId(), activity, winAward);

        // 发送MQ消息通知
        userActivityOrderService.publishCreateOrderEvent(userActivityOrder);

        return ActionResult.success(winAward);
    }

    private void publishWinAwardEvent(String userId, Activity activity, WinAward winAward) {
        UserWinAwardDto dto = new UserWinAwardDto();
        dto.setAwardId(winAward.getAwardId());
        dto.setAwardName(winAward.getAwardName());
        dto.setAwardContent(winAward.getAwardContent());
        dto.setAwardType(winAward.getAwardType());

        dto.setActivityId(activity.getActivityId());
        dto.setActivityName(activity.getActivityName());
        dto.setActivityType(activity.getActivityType());
        dto.setStrategyId(activity.getStrategyId());
        dto.setUserId(userId);

        publishWinAwardEvent(dto);
    }

    @Override
    public void publishWinAwardEvent(UserWinAwardDto winAwardDto) {
        Event event = new Event();
        event.setType(Event.Type.USER_WIN_AWARD);
        event.setBody(winAwardDto);
        eventProducer.publishWithRecord(event);
    }
}




