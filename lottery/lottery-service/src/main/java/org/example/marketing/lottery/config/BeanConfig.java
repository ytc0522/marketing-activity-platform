package org.example.marketing.lottery.config;

import org.example.marketing.lottery.domain.pool.DynamicChanceLotteryAwardPool;
import org.example.marketing.lottery.domain.pool.FixChanceLotteryAwardPool;
import org.example.marketing.lottery.domain.pool.ILotteryAwardPool;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class BeanConfig {

    @Resource
    private FixChanceLotteryAwardPool fixChanceLotteryAwardPool;

    @Resource
    private DynamicChanceLotteryAwardPool dynamicChanceLotteryAwardPool;


    @Bean
    public Map<Integer, ILotteryAwardPool> lotteryAwardPoolMap () {
        Map<Integer, ILotteryAwardPool> map = new HashMap<>();
        map.put(0,fixChanceLotteryAwardPool);
        map.put(1,dynamicChanceLotteryAwardPool);
        return map;
    }


}
