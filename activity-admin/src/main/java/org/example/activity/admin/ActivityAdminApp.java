package org.example.activity.admin;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("org.example.activity.repository.mapper")
@SpringBootApplication
public class ActivityAdminApp {


    public static void main(String[] args) {
        SpringApplication.run(ActivityAdminApp.class);
    }


}
