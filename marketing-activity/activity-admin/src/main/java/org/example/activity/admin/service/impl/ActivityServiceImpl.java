package org.example.activity.admin.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import marketing.activity.infrastructure.repository.entity.Activity;
import marketing.activity.infrastructure.repository.mapper.ActivityMapper;
import marketing.lottery.rpc.ILotteryRpcService;
import marketing.lottery.rpc.dto.ActionResult;
import org.apache.dubbo.config.annotation.DubboReference;
import org.example.activity.admin.service.ActivityService;
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
    private ILotteryRpcService lotteryDraw;


    @Override
    public ActionResult prepare(Long activityId) {
        Activity activity = this.lambdaQuery().eq(Activity::getActivityId, activityId)
                .one();
        if (activity == null) {
            return ActionResult.failure("活动不存在");
        }

        lotteryDraw.prepare(activity.getStrategyId());
        return ActionResult.success();
    }
}




