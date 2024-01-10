package org.example.marketing.activity.consumer;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.cloud.stream.messaging.Source;


@EnableBinding({Source.class, Sink.class})
@MapperScan("org.example.activity.repository.mapper")
@SpringBootApplication
public class ActivityConsumerApp {

    public static void main(String[] args) {
        SpringApplication.run(ActivityConsumerApp.class);
    }
}
