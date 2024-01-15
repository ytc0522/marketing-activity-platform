package org.example.marketing.activity.order.facade;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.dubbo.config.annotation.Service;
import org.example.marketing.activity.order.repository.entity.UserActivityOrder;
import org.example.marketing.activity.order.service.UserActivityOrderService;
import org.example.marketing.common.dto.ActivityOrderDto;
import org.example.marketing.common.rpc.order.ActivityOrderServiceFacade;

import javax.annotation.Resource;

@Service
public class ActivityOrderServiceFacadeImpl implements ActivityOrderServiceFacade {

    @Resource
    private UserActivityOrderService activityOrderService;


    @Override
    public ActivityOrderDto getByOrderId(String orderId) {
        UserActivityOrder userActivityOrder = activityOrderService.getByOrderId(orderId);
        ActivityOrderDto activityOrderDto = BeanUtil.copyProperties(userActivityOrder, ActivityOrderDto.class);
        return activityOrderDto;

    }
}
