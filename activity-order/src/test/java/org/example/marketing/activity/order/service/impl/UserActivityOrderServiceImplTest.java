package org.example.marketing.activity.order.service.impl;

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
        //activityOrderService.lambdaQuery().page()
    }
}