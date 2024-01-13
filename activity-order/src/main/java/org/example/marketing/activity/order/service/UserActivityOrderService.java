package org.example.marketing.activity.order.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.example.marketing.activity.order.repository.entity.UserActivityOrder;

/**
 * @author jack
 * @description 针对表【user_activity_order_0】的数据库操作Service
 * @createDate 2024-01-13 16:03:22
 */
public interface UserActivityOrderService extends IService<UserActivityOrder> {


    /**
     * 根据订单编号查询订单
     *
     * @param orderId
     * @return
     */
    UserActivityOrder getByOrderId(String orderId);

    // todo 分页查询


    //


}
