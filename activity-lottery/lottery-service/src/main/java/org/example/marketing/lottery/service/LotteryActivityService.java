package org.example.marketing.lottery.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.example.marketing.lottery.repository.entity.LotteryActivity;
import org.example.marketing.lottery.rpc.dto.LotteryActivityRich;

/**
 * @author jack
 * @createDate 2024-01-07 20:39:51
 */
public interface LotteryActivityService extends IService<LotteryActivity> {


    LotteryActivityRich refreshCache(Long lotteryActivityId);

    LotteryActivityRich getFromCache(Long lotteryActivityId);


}
