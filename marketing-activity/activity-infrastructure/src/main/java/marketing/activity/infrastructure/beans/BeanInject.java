package marketing.activity.infrastructure.beans;

import cn.hutool.core.lang.Snowflake;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class BeanInject {

    @Bean
    public Snowflake snowflake() {
        return new Snowflake();
    }


}
