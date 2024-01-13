package org.example.marketing.activity.order.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.example.marketing.activity.order.repository.entity.UserActivityOrder;
import org.example.marketing.activity.order.repository.mapper.UserActivityOrderMapper;
import org.example.marketing.activity.order.service.UserActivityOrderService;
import org.springframework.stereotype.Service;

/**
 * @author jack
 * @description 针对表【user_activity_order_0】的数据库操作Service实现
 * @createDate 2024-01-13 16:03:22
 */
@Service
public class UserActivityOrderServiceImpl extends ServiceImpl<UserActivityOrderMapper, UserActivityOrder>
        implements UserActivityOrderService {


    @Override
    public UserActivityOrder getByOrderId(String orderId) {
        QueryWrapper<UserActivityOrder> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("order_id", orderId);
        UserActivityOrder userActivityOrder = baseMapper.selectOne(queryWrapper);
        return userActivityOrder;
    }
}




