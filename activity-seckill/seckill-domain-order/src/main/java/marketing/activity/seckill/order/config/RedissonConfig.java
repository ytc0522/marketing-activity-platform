package marketing.activity.seckill.order.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RedissonConfig {

    /**
     * 所有对redisson的使用都是通过RedissonClient来操作的
     *
     * @return
     */

    @Value("${spring.redis.port}")
    private String port;

    @Value("${spring.redis.host}")
    private String host;

    @Bean(destroyMethod = "shutdown")
    public RedissonClient redisson() {
        // 1. 创建配置
        Config config = new Config();
        // 一定要加redis://
        config.useSingleServer().setAddress("redis://" + host + ":" + port);
        // 2. 根据config创建出redissonClient实例
        RedissonClient redissonClient = Redisson.create(config);
        return redissonClient;
    }


}
