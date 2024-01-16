package marketing.activity.order;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableDubbo
@MapperScan({"marketing.activity.order.repository.mapper"})
@SpringBootApplication
public class LotteryOrderServiceApp {

    public static void main(String[] args) {
        SpringApplication.run(LotteryOrderServiceApp.class);
    }


}
