package marketing.activity.base;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("marketing.activity.base.infrastructure.repository")
@SpringBootApplication
public class ActivityBaseApplication {

    public static void main(String[] args) {
        SpringApplication.run(ActivityBaseApplication.class);
    }
}
