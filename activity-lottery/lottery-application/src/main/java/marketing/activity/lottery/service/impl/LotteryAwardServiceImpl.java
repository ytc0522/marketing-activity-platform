package marketing.activity.lottery.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import marketing.activity.lottery.infrastructure.repository.entity.LotteryAward;
import marketing.activity.lottery.infrastructure.repository.mapper.LotteryAwardMapper;
import marketing.activity.lottery.service.LotteryAwardService;
import org.springframework.stereotype.Service;


@Service
public class LotteryAwardServiceImpl extends ServiceImpl<LotteryAwardMapper, LotteryAward>
        implements LotteryAwardService {

    private static String LOTTERY_AWARD_PREFIX_KEY = "LOTTERY_AWARD:";




}




