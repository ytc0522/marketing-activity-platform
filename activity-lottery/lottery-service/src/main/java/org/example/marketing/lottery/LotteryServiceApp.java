package org.example.marketing.lottery;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("org.example.marketing.lottery.repository.mapper")
@EnableDubbo
@SpringBootApplication
public class LotteryServiceApp {

    public static void main(String[] args) {
        SpringApplication.run(LotteryServiceApp.class);
    }


}
