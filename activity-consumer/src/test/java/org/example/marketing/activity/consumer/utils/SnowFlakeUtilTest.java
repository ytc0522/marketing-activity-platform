package org.example.marketing.activity.consumer.utils;


import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SnowFlakeUtilTest {

    @Resource
    private SnowFlakeUtil snowFlakeUtil;

    @Test
    public void snowflakeId() {
        long id = snowFlakeUtil.snowflakeId();
        System.out.println("id = " + id);
        System.out.println("id.length = " + String.valueOf(id).length());
    }
}