package marketing.activity.seckill.order;


import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableDubbo
@MapperScan("marketing.activity.seckill.infrastructure.repository.mapper")
@SpringBootApplication
public class SeckillOrderServiceApp {
    public static void main(String[] args) {
        SpringApplication.run(SeckillOrderServiceApp.class);
    }

}
