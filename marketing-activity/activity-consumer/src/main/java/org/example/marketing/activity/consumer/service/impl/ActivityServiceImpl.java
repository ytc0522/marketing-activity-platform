package org.example.marketing.activity.consumer.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import marketing.activity.infrastructure.repository.entity.Activity;
import marketing.activity.infrastructure.repository.entity.UserTakeActivityRecord;
import marketing.activity.infrastructure.repository.mapper.ActivityMapper;
import marketing.lottery.rpc.ILotteryRpcService;
import marketing.lottery.rpc.dto.WinAward;
import marketing.lottery.rpc.req.LotteryDrawReq;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboReference;
import org.example.marketing.activity.consumer.mq.producer.EventProducer;
import org.example.marketing.activity.consumer.service.ActivityService;
import org.example.marketing.activity.consumer.service.AwardService;
import org.example.marketing.activity.consumer.service.UserTakeActivityRecordService;
import org.example.marketing.common.ActionResult;
import org.example.marketing.common.dto.UserWinAwardDto;
import org.example.marketing.common.enums.ActivityType;
import org.example.marketing.common.mq.Event;
import org.example.marketing.common.req.activity.TakeActivityReq;
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
    private ILotteryRpcService lotteryDraw;

    @Resource
    private AwardService awardService;

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
            LotteryDrawReq lotteryDrawReq = new LotteryDrawReq();
            lotteryDrawReq.setUserId(req.getUserId());
            lotteryDrawReq.setActivityId(activity.getActivityId());
            // 假如奖品库存更新成功了，但是订单创建失败了，怎么处理？
            // 这里 redis 和 数据库 无法保证事务一致性，在这里，无需保证强一致性，无非就是 redis扣减库存成功，
            // 但是 数据库没有插入记录，我们已数据库记录为准。
            //winAward = lotteryDraw.draw(lotteryDrawReq);
        }

        // 更新领取活动记录表 可以异步更新
//        record.setState("1");
//        userTakeActivityRecordService.updateById(record);

        // 增加用户参加的次数 更新用户活动次数表即可。
        if (winAward == null || StringUtils.isEmpty(winAward.getAwardId())) {
            // 未中奖
            return result;
        }

        //

        // 中奖了 保存中奖订单
        // 同步还是异步保存？这里可以通过MQ异步保存订单，然后在订单服务中发送订单已经创建的通知。
        // 假如库存已经更新了，但是没来得及发送消息服务就挂了，这个时候怎么办？
        publishWinAwardEvent(req.getUserId(), activity, winAward);

        return ActionResult.success(winAward);
    }

    private void publishWinAwardEvent(String userId, Activity activity, WinAward winAward) {
        UserWinAwardDto dto = new UserWinAwardDto();
        dto.setAwardId(winAward.getAwardId());
        dto.setAwardName(winAward.getAwardName());


        dto.setActivityId(activity.getActivityId());
        dto.setActivityName(activity.getActivityName());
        dto.setActivityType(activity.getActivityType());
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




