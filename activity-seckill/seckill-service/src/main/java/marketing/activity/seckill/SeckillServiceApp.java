package marketing.activity.seckill;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableDubbo
@MapperScan("marketing.activity.seckill.repository.mapper")
@SpringBootApplication
public class SeckillServiceApp {


    public static void main(String[] args) {
        SpringApplication.run(SeckillServiceApp.class);
    }
}
