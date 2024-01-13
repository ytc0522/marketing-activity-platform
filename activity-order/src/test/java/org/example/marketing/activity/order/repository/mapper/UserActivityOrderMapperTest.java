package org.example.marketing.activity.order.repository.mapper;

import cn.hutool.core.lang.Snowflake;
import org.example.marketing.activity.order.repository.entity.UserActivityOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserActivityOrderMapperTest {

    @Resource
    private UserActivityOrderMapper orderMapper;

    @Test
    public void testInsert() {
        Snowflake snowflake = new Snowflake();
        UserActivityOrder order = new UserActivityOrder();
        String orderId = snowflake.nextIdStr();
        System.out.println("orderId = " + orderId);
        order.setOrderId(orderId);
        order.setUserId("10000");
        order.setActivityId(10001L);
        order.setAwardId("99999");
        order.setAwardName("优惠券");
        order.setAwardContent("xkksjjgkolell");

        int insert = orderMapper.insert(order);
        System.out.println("insert = " + insert);


    }


}