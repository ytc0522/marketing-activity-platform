package marketing.lottery.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import marketing.lottery.repository.entity.LotteryAward;
import marketing.lottery.repository.mapper.LotteryAwardMapper;
import marketing.lottery.service.LotteryAwardService;
import org.springframework.stereotype.Service;


@Service
public class LotteryAwardServiceImpl extends ServiceImpl<LotteryAwardMapper, LotteryAward>
        implements LotteryAwardService {

    private static String LOTTERY_AWARD_PREFIX_KEY = "LOTTERY_AWARD:";




}



