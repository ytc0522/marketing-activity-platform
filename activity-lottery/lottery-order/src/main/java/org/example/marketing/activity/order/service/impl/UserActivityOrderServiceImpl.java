package org.example.marketing.activity.order.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.example.marketing.activity.order.dto.req.ActivityOrderQueryReq;
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

    @Override
    public IPage<UserActivityOrder> queryPage(ActivityOrderQueryReq req) {
        Page<UserActivityOrder> page = new Page<>(req.getCurrentPage(), req.getSize());

        LambdaQueryChainWrapper<UserActivityOrder> wrapper = new LambdaQueryChainWrapper<UserActivityOrder>(baseMapper);

        Page<UserActivityOrder> pageResult = wrapper.eq(req.getOrderId() != null, UserActivityOrder::getOrderId, req.getOrderId())
                .eq(req.getOrderStatus() != null, UserActivityOrder::getOrderStatus, req.getOrderStatus())
                .eq(req.getActivityId() != null, UserActivityOrder::getActivityId, req.getActivityId())
                .eq(req.getActivityType() != null, UserActivityOrder::getActivityType, req.getActivityType())
                .eq(req.getUserId() != null, UserActivityOrder::getUserId, req.getUserId())
                .eq(req.getMchId() != null, UserActivityOrder::getMerchantId, req.getMchId())
                .gt(req.getCreateTimeBegin() != null, UserActivityOrder::getCreateTime, req.getCreateTimeBegin())
                .lt(req.getCreateTimeEnd() != null, UserActivityOrder::getCreateTime, req.getCreateTimeEnd())
                .page(page);

        return pageResult;
    }
}




