package org.example.marketing.activity.consumer.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.example.activity.repository.entity.Activity;
import org.example.marketing.common.ActionResult;
import org.example.marketing.common.dto.UserWinAwardDto;
import org.example.marketing.common.req.activity.TakeActivityReq;

/**
* @author jack
* @description 针对表【activity(活动配置)】的数据库操作Service
* @createDate 2024-01-07 01:13:48
*/
public interface ActivityService extends IService<Activity> {


    /**
     * 参加活动
     * @param req
     * @return
     */
    public ActionResult takeActivity(TakeActivityReq req);


    /**
     * 发布 用户参加活动获得奖品 事件
     */
    public void publishWinAwardEvent(UserWinAwardDto winAwardDto);



}
