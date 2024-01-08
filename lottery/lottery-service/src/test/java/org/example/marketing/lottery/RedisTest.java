package org.example.marketing.lottery;


import org.example.marketing.lottery.repository.entity.Lottery;
import org.example.marketing.lottery.repository.util.RedisUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
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
        Lottery lottery = new Lottery();
        lottery.setId(100L);
        lottery.setChanceType(1);
        lottery.setCreateTime(new Date());

        boolean testKey = redisUtil.set("testKey", lottery);

        Lottery lottery1 = (Lottery)redisUtil.get("testKey");

        System.out.println("lottery1 = " + lottery1);


    }


}
