package org.example.marketing.lottery.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.example.marketing.lottery.repository.entity.LotteryDetail;
import org.example.marketing.lottery.repository.mapper.LotteryDetailMapper;
import org.example.marketing.lottery.service.LotteryDetailService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
* @author jack
* @description 针对表【lottery(策略明细)】的数据库操作Service实现
* @createDate 2024-01-07 19:47:12
*/
@Service
public class LotteryDetailServiceImpl extends ServiceImpl<LotteryDetailMapper, LotteryDetail>
    implements LotteryDetailService {

    private static String LOTTERY_AWARD_PREFIX_KEY = "LOTTERY_AWARD:";


    @Resource
    private RedisTemplate redisTemplate;


    private void initToCache(Long lotteryId) {




    }



}




