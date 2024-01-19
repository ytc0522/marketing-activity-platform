package marketing.activity.seckill;


import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableDubbo
@MapperScan("marketing.activity.seckill.infrastructure.repository.mapper")
@SpringBootApplication(scanBasePackages = {"marketing.activity"})
public class SeckillApplication {


    public static void main(String[] args) {
        SpringApplication.run(SeckillApplication.class);
    }
}
