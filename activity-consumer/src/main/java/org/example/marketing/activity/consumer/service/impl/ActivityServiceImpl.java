package org.example.marketing.activity.consumer.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.dubbo.config.annotation.DubboReference;
import org.example.activity.repository.entity.Activity;
import org.example.activity.repository.mapper.ActivityMapper;
import org.example.marketing.activity.consumer.service.ActivityService;
import org.example.marketing.common.ActionResult;
import org.example.marketing.common.enums.ActivityType;
import org.example.marketing.common.req.activity.TakeActivityReq;
import org.example.marketing.lottery.rpc.ILotteryDraw;
import org.example.marketing.lottery.rpc.req.DrawReq;
import org.springframework.stereotype.Service;

/**
 * @author jack
 * @description 针对表【activity(活动配置)】的数据库操作Service实现
 * @createDate 2024-01-07 01:13:48
 */
@Service
public class ActivityServiceImpl extends ServiceImpl<ActivityMapper, Activity>
        implements ActivityService {

    @DubboReference
    private ILotteryDraw lotteryDraw;


    @Override
    public ActionResult takeActivity(TakeActivityReq req) {

        Long activityId = req.getActivityId();
        Activity activity = this.getById(activityId);

        if (activity == null) {
            return ActionResult.failure("活动不存在");
        }

        // 校验活动时间
        // 校验用户活动参与次数
        // 风控-校验用户IP、用户ID是否在黑名单中。


        // 活动类型
        String activityType = activity.getActivityType();
        if (ActivityType.LOTTERY.toString().equals(activityType)) {
            DrawReq drawReq = new DrawReq();
            drawReq.setUserId(req.getUserId());
            drawReq.setLotteryId(activity.getStrategyId());
            return lotteryDraw.draw(drawReq);
        }


        return ActionResult.success();
    }
}




