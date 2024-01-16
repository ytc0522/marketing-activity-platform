package marketing.activity.lottery.order;

import marketing.activity.lottery.order.dto.LotteryOrderDto;
import marketing.activity.lottery.order.dto.LotteryWinAwardDto;

public interface ILotteryOrderRpcService {


    /**
     * 根据奖品创建订单
     *
     * @param winAwardDto 奖品
     * @param orderId     订单ID，要唯一，防止重复生成相同订单
     * @return 订单
     */
    LotteryOrderDto createOrder(LotteryWinAwardDto winAwardDto, String orderId);


}
