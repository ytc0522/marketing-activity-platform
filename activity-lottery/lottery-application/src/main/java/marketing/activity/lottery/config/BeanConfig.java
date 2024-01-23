package marketing.activity.lottery.config;

import marketing.activity.lottery.domain.pool.DynamicChanceLotteryLotteryAwardPool;
import marketing.activity.lottery.domain.pool.FixChanceLotteryLotteryAwardPool;
import marketing.activity.lottery.domain.pool.ILotteryAwardPool;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class BeanConfig {

    @Resource
    private FixChanceLotteryLotteryAwardPool fixChanceLotteryAwardPool;

    @Resource
    private DynamicChanceLotteryLotteryAwardPool dynamicChanceLotteryAwardPool;


    @Bean
    public Map<Integer, ILotteryAwardPool> lotteryAwardPoolMap () {
        Map<Integer, ILotteryAwardPool> map = new HashMap<>();
        map.put(0,fixChanceLotteryAwardPool);
        map.put(1,dynamicChanceLotteryAwardPool);
        return map;
    }


}
