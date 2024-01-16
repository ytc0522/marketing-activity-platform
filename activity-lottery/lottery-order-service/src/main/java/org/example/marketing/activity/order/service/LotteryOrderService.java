package org.example.marketing.activity.order.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import org.example.marketing.activity.order.dto.req.ActivityOrderQueryReq;
import org.example.marketing.activity.order.repository.entity.LotteryOrder;

/**
 * @author jack
 * @description 针对表【lottery_order】的数据库操作Service
 * @createDate 2024-01-13 16:03:22
 */
public interface LotteryOrderService extends IService<LotteryOrder> {


    /**
     * 根据订单编号查询订单
     *
     * @param orderId
     * @return
     */
    LotteryOrder getByOrderId(String orderId);


    IPage<LotteryOrder> queryPage(ActivityOrderQueryReq req);


}
