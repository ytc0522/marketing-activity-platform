package org.example.marketing.lottery.repository.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.example.marketing.lottery.repository.entity.Lottery;
import org.example.marketing.lottery.repository.service.LotteryService;
import org.example.marketing.lottery.repository.mapper.LotteryMapper;
import org.springframework.stereotype.Service;

/**
* @author jack
* @description 针对表【lottery】的数据库操作Service实现
* @createDate 2024-01-07 20:39:51
*/
@Service
public class LotteryServiceImpl extends ServiceImpl<LotteryMapper, Lottery>
    implements LotteryService{

}




