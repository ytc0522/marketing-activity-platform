package org.example.marketing.activity.order;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan({"org.example.marketing.activity.order.repository.mapper", "org.example.activity.repository.mapper"})
@SpringBootApplication
public class ActivityOrderApp {

    public static void main(String[] args) {
        SpringApplication.run(ActivityOrderApp.class);
    }


}
