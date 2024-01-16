package org.example.marketing.activity.order.event.handler.impl;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.fastjson.JSON;
import org.example.marketing.activity.order.event.handler.IEventHandler;
import org.example.marketing.activity.order.mq.producer.EventProducer;
import org.example.marketing.activity.order.repository.entity.LotteryOrder;
import org.example.marketing.activity.order.repository.mapper.LotteryOrderMapper;
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
    private LotteryOrderMapper orderMapper;

    @Resource
    private RedisUtil redisUtil;

    @Resource
    private EventProducer eventProducer;


    @Override
    public void handle(Event event) {
        String jsonString = JSON.toJSONString(event.getBody());
        UserWinAwardDto dto = JSON.parseObject(jsonString, UserWinAwardDto.class);

        LotteryOrder lotteryOrder = BeanUtil.copyProperties(dto, LotteryOrder.class);

        Date currentDate = new Date();

        // 订单号如何生成？ 订单ID = 下单时间时间戳 + redis自增生成数 +  用户ID 后4位

        lotteryOrder.setOrderId(genOrderId(dto.getUserId(), currentDate));
        lotteryOrder.setStatus(0);
        lotteryOrder.setUuid(event.getEventId());
        lotteryOrder.setCreateTime(currentDate);
        lotteryOrder.setUpdateTime(currentDate);
        orderMapper.insert(lotteryOrder);

        // 发布订单创建事件
        Event orderCreatedEvent = new Event();
        orderCreatedEvent.setBody(lotteryOrder.getOrderId());
        orderCreatedEvent.setType(Event.Type.ACTIVITY_ORDER_CREATED);

        eventProducer.publishWithRecord(orderCreatedEvent);

    }

    @Override
    public Event.Type supportedEventType() {
        return Event.Type.USER_WIN_AWARD;
    }


    /**
     * 生成OrderId
     * 订单ID = 下单时间时间戳 + redis自增生成数 +  用户ID 后4位
     *
     * @param userId
     * @param createDate
     * @return
     */
    private String genOrderId(String userId, Date createDate) {
        StringBuilder orderIdStringBuilder = new StringBuilder(String.valueOf(createDate.getTime()));

        // 通过Redis生成一个自增唯一的数字
        long incr = redisUtil.incr(createDate.getTime() + ":" + userId, 1);
        orderIdStringBuilder.append(incr);

        if (userId.length() >= 4) {
            orderIdStringBuilder.append(userId, userId.length() - 4, -1);
        } else {
            for (int i = 0; i < 4 - userId.length(); i++) {
                orderIdStringBuilder.append("0");
            }
            orderIdStringBuilder.append(userId);
        }
        return orderIdStringBuilder.toString();
    }
}
