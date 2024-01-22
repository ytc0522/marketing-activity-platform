package marketing.lottery.service;

import com.baomidou.mybatisplus.extension.service.IService;
import marketing.lottery.repository.entity.LotteryActivity;
import marketing.lottery.rpc.dto.LotteryActivityRich;

/**
 * @author jack
 * @createDate 2024-01-07 20:39:51
 */
public interface LotteryActivityService extends IService<LotteryActivity> {


    LotteryActivityRich refreshCache(Long lotteryActivityId);

    LotteryActivityRich getFromCache(Long lotteryActivityId);


}
