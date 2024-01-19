package org.example.activity.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import marketing.activity.infrastructure.repository.entity.Activity;
import marketing.lottery.rpc.dto.ActionResult;

/**
* @author jack
* @description 针对表【activity(活动配置)】的数据库操作Service
* @createDate 2024-01-07 01:13:48
*/
public interface ActivityService extends IService<Activity> {


    ActionResult prepare(Long activityId);

}
