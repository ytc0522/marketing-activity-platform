package marketing.activity.lottery.service;

import com.baomidou.mybatisplus.extension.service.IService;
import marketing.activity.lottery.infrastructure.repository.entity.LotteryActivity;
import marketing.lottery.rpc.dto.ActionResult;
import marketing.lottery.rpc.dto.LotteryActivityRich;
import marketing.lottery.rpc.req.LotteryDrawReq;

/**
 * @author jack
 * @createDate 2024-01-07 20:39:51
 */
public interface LotteryActivityService extends IService<LotteryActivity> {


    ActionResult draw(LotteryDrawReq req);


    LotteryActivityRich refreshCache(Long lotteryActivityId);

    LotteryActivityRich query(Long lotteryActivityId);


}
