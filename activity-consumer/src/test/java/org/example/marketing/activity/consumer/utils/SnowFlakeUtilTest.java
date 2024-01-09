package org.example.marketing.activity.consumer.utils;


import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

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