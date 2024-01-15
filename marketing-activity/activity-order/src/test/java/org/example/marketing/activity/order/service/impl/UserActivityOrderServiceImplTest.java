package org.example.marketing.activity.order.service.impl;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.example.marketing.activity.order.dto.req.ActivityOrderQueryReq;
import org.example.marketing.activity.order.repository.entity.UserActivityOrder;
import org.example.marketing.activity.order.service.UserActivityOrderService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserActivityOrderServiceImplTest {


    @Resource
    private UserActivityOrderService activityOrderService;

    @Test
    public void getByOrderId() {
        UserActivityOrder userActivityOrder = activityOrderService.getByOrderId("1745815005089697792");
        System.out.println("userActivityOrder = " + userActivityOrder);
    }


    @Test
    public void test_queryPage() {

        DateTime date = DateUtil.parse("2024-01-13", DatePattern.NORM_DATE_FORMAT);

        ActivityOrderQueryReq req = ActivityOrderQueryReq.builder()
                .userId("10000")
                .createTimeBegin(date)
                .build();

        req.setCurrentPage(1);
        req.setSize(10);

        long currentTimeMillis = System.currentTimeMillis();
        IPage<UserActivityOrder> result = activityOrderService.queryPage(req);
        long now = System.currentTimeMillis();

        long diff = now - currentTimeMillis;

        System.out.println("diff = " + diff);


        long total = result.getTotal();
        System.out.println("total = " + total);

        for (UserActivityOrder record : result.getRecords()) {
            System.out.println("record = " + record);
        }
    }
}