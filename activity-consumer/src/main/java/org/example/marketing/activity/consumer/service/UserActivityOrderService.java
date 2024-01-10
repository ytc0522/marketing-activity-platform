package org.example.marketing.activity.consumer.service;

import org.example.activity.repository.entity.Activity;
import org.example.activity.repository.entity.UserActivityOrder;
import com.baomidou.mybatisplus.extension.service.IService;
import org.example.marketing.lottery.rpc.dto.WinAward;

/**
 * @author jack
 * @description 针对表【user_activity_order】的数据库操作Service
 * @createDate 2024-01-09 17:14:29
 */
public interface UserActivityOrderService extends IService<UserActivityOrder> {


    /**
     * 用户中奖后保存用户中奖订单
     * @param userId
     * @param activity
     * @param winAward
     * @return
     */
    UserActivityOrder saveWinAwardOrder(String userId, Activity activity, WinAward winAward);

}
