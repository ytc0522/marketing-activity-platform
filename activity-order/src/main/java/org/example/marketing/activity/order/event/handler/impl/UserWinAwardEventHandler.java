package org.example.marketing.activity.order.event.handler.impl;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.fastjson.JSON;
import org.example.marketing.activity.order.event.handler.IEventHandler;
import org.example.marketing.activity.order.repository.entity.UserActivityOrder;
import org.example.marketing.activity.order.repository.mapper.UserActivityOrderMapper;
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


    @Override
    public void handle(Event event) {
        String jsonString = JSON.toJSONString(event.getBody());
        UserWinAwardDto dto = JSON.parseObject(jsonString, UserWinAwardDto.class);

        UserActivityOrder userActivityOrder = BeanUtil.copyProperties(dto, UserActivityOrder.class);
        userActivityOrder.setOrderId(String.valueOf(snowFlakeUtil.snowflakeId()));
        userActivityOrder.setOrderStatus("1");
        userActivityOrder.setCreateTime(new Date());
        userActivityOrder.setUpdateTime(new Date());
        orderMapper.insert(userActivityOrder);
    }

    @Override
    public Event.Type supportedEventType() {
        return Event.Type.USER_WIN_AWARD;
    }
}
