package org.example.activity.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.example.activity.repository.entity.Activity;
import org.example.marketing.common.ActionResult;

/**
* @author jack
* @description 针对表【activity(活动配置)】的数据库操作Service
* @createDate 2024-01-07 01:13:48
*/
public interface ActivityService extends IService<Activity> {


    ActionResult prepare(Long activityId);

}
