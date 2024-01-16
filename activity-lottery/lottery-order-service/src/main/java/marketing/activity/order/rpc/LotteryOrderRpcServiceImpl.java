package marketing.activity.order.rpc;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.dubbo.config.annotation.Service;
import marketing.activity.lottery.order.ILotteryOrderRpcService;
import marketing.activity.lottery.order.dto.LotteryOrderDto;
import marketing.activity.lottery.order.dto.LotteryWinAwardDto;
import marketing.activity.order.repository.entity.LotteryOrder;
import marketing.activity.order.repository.mapper.LotteryOrderMapper;
import marketing.activity.order.utils.SnowFlakeUtil;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.Resource;
import java.util.Date;

@Service
public class LotteryOrderRpcServiceImpl implements ILotteryOrderRpcService {


    @Resource
    private LotteryOrderMapper lotteryOrderMapper;

    @Resource
    SnowFlakeUtil snowFlakeUtil;

    @Override
    public LotteryOrderDto createOrder(LotteryWinAwardDto dto, String orderId) {
        LotteryOrder lotteryOrder = new LotteryOrder();
        // 订单ID简单的使用雪花算法生成
        if (StringUtils.isNotEmpty(orderId)) {
            lotteryOrder.setOrderId(orderId);
        } else {
            lotteryOrder.setOrderId(String.valueOf(snowFlakeUtil.snowflakeId()));
        }
        lotteryOrder.setUserId(dto.getUserId());
        lotteryOrder.setAwardId(dto.getAwardId());
        lotteryOrder.setAwardName(dto.getAwardName());
        lotteryOrder.setActivityId(dto.getActivityId());
        lotteryOrder.setOrderStatus(0);
        lotteryOrder.setCreateTime(new Date());
        lotteryOrder.setUpdateTime(new Date());

        int insert = lotteryOrderMapper.insert(lotteryOrder);
        if (insert == 0) {
            throw new RuntimeException("插入失败");
        }

        LotteryOrderDto lotteryOrderDto = BeanUtil.copyProperties(lotteryOrder, LotteryOrderDto.class);

        return lotteryOrderDto;
    }


}
