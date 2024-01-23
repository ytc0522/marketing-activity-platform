package marketing.activity.lottery;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("marketing.activity.lottery.infrastructure.repository.mapper")
@EnableDubbo
@SpringBootApplication(scanBasePackages = {"marketing.activity"})
public class LotteryServiceApp {

    public static void main(String[] args) {
        SpringApplication.run(LotteryServiceApp.class);
    }


}
