package org.example.marketing.activity.order.event.handler.impl;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.fastjson.JSON;
import org.example.marketing.activity.order.event.handler.IEventHandler;
import org.example.marketing.activity.order.mq.producer.EventProducer;
import org.example.marketing.activity.order.repository.entity.UserActivityOrder;
import org.example.marketing.activity.order.repository.mapper.UserActivityOrderMapper;
import org.example.marketing.activity.order.utils.RedisUtil;
import org.example.marketing.activity.order.utils.SnowFlakeUtil;
import org.example.marketing.common.dto.UserWinAwardDto;
import org.example.marketing.common.mq.Event;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;

/**
 * 用户获得奖品事件处理器
 */
@Component
public class UserWinAwardEventHandler implements IEventHandler {


    @Resource
    private SnowFlakeUtil snowFlakeUtil;

    @Resource
    private UserActivityOrderMapper orderMapper;

    @Resource
    private RedisUtil redisUtil;

    @Resource
    private EventProducer eventProducer;


    @Override
    public void handle(Event event) {
        String jsonString = JSON.toJSONString(event.getBody());
        UserWinAwardDto dto = JSON.parseObject(jsonString, UserWinAwardDto.class);

        UserActivityOrder userActivityOrder = BeanUtil.copyProperties(dto, UserActivityOrder.class);

        Date currentDate = new Date();

        // 订单号如何生成？ 订单ID = 下单时间时间戳 + redis自增生成数 +  用户ID 后4位
        StringBuilder orderIdStringBuilder = new StringBuilder(String.valueOf(currentDate.getTime()));

        // 通过Redis生成一个自增唯一的数字
        long incr = redisUtil.incr(currentDate.getTime() + ":" + dto.getUserId(), 1);
        orderIdStringBuilder.append(incr);

        String userId = String.valueOf(dto.getUserId());
        if (userId.length() >= 4) {
            orderIdStringBuilder.append(userId, userId.length() - 4, -1);
        } else {
            for (int i = 0; i < 4 - userId.length(); i++) {
                orderIdStringBuilder.append("0");
            }
            orderIdStringBuilder.append(userId);
        }
        userActivityOrder.setOrderId(orderIdStringBuilder.toString());
        userActivityOrder.setOrderStatus("0");
        userActivityOrder.setCreateTime(currentDate);
        userActivityOrder.setUpdateTime(currentDate);
        orderMapper.insert(userActivityOrder);

        // 发布订单创建事件
        Event orderCreatedEvent = new Event();
        orderCreatedEvent.setBody(userActivityOrder.getOrderId());
        orderCreatedEvent.setType(Event.Type.ACTIVITY_ORDER_CREATED);

        eventProducer.publishWithRecord(orderCreatedEvent);

    }

    @Override
    public Event.Type supportedEventType() {
        return Event.Type.USER_WIN_AWARD;
    }
}
