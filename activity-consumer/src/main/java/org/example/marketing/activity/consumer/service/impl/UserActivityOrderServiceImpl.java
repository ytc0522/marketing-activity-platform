package org.example.marketing.activity.consumer.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.example.activity.repository.entity.Activity;
import org.example.activity.repository.entity.UserActivityOrder;
import org.example.activity.repository.mapper.UserActivityOrderMapper;
import org.example.marketing.activity.consumer.mq.Event;
import org.example.marketing.activity.consumer.mq.producer.EventProducer;
import org.example.marketing.activity.consumer.service.UserActivityOrderService;
import org.example.marketing.activity.consumer.utils.SnowFlakeUtil;
import org.example.marketing.lottery.rpc.dto.WinAward;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @author jack
 * @description 针对表【user_activity_order】的数据库操作Service实现
 * @createDate 2024-01-09 17:14:29
 */
@Slf4j
@Service
public class UserActivityOrderServiceImpl extends ServiceImpl<UserActivityOrderMapper, UserActivityOrder>
        implements UserActivityOrderService {


    @Resource
    private SnowFlakeUtil snowFlakeUtil;

    @Resource
    private EventProducer eventProducer;

    @Override
    public UserActivityOrder saveWinAwardOrder(String userId, Activity activity, WinAward winAward) {
        // 中奖了
        // 记录中奖记录，生成活动订单
        UserActivityOrder userActivityOrder = new UserActivityOrder();
        userActivityOrder.setOrderStatus("1");
        userActivityOrder.setUserId(userId);
        // 活动信息
        userActivityOrder.setActivityId(activity.getActivityId());
        userActivityOrder.setActivityType(activity.getActivityType());
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

        String orderString = JSON.toJSONString(userActivityOrder);
        log.info("【保存活动订单】{}", JSON.toJSONString(orderString));
        boolean saved = this.save(userActivityOrder);
        if (!saved) {
            log.error("【保存活动订单失败】orderId:{}", userActivityOrder.getOrderId());
        }
        return userActivityOrder;
    }

    @Override
    public void publishCreateOrderEvent(UserActivityOrder userActivityOrder) {
        // 发送MQ消息 通知其他系统 有用户中奖了
        Event event = new Event();
        event.setBody(userActivityOrder.getOrderId());
        event.setType(Event.Type.ACTIVITY_ORDER_CREATE);
        boolean published = eventProducer.publish(event);
        // 发送成功
        if (published) {
            // 更新
            userActivityOrder.setCreateEventSendState("1");
        } else {
            //
            userActivityOrder.setCreateEventSendState("2");
        }
        this.updateById(userActivityOrder);
    }
}




