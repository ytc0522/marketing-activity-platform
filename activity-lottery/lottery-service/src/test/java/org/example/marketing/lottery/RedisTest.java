package org.example.marketing.lottery;


import org.example.marketing.lottery.repository.entity.LotteryActivity;
import org.example.marketing.lottery.repository.util.RedisUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisTest {

    @Resource
    private RedisUtil redisUtil;


    @Test
    public void test_list() {
        LotteryActivity lotteryActivity = new LotteryActivity();
        lotteryActivity.setId(100L);
        lotteryActivity.setProbabilityType(1);
        lotteryActivity.setCreateTime(new Date());

        boolean testKey = redisUtil.set("testKey", lotteryActivity);

        LotteryActivity lotteryActivity1 = (LotteryActivity) redisUtil.get("testKey");

        System.out.println("lottery1 = " + lotteryActivity1);


    }


}
