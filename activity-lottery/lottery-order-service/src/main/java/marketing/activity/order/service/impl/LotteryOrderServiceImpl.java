package marketing.activity.order.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import marketing.activity.order.dto.req.ActivityOrderQueryReq;
import marketing.activity.order.repository.entity.LotteryOrder;
import marketing.activity.order.repository.mapper.LotteryOrderMapper;
import marketing.activity.order.service.LotteryOrderService;
import marketing.activity.order.utils.SnowFlakeUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author jack
 * @description 针对表【lottery_order】的数据库操作Service实现
 * @createDate 2024-01-13 16:03:22
 */
@Service
public class LotteryOrderServiceImpl extends ServiceImpl<LotteryOrderMapper, LotteryOrder>
        implements LotteryOrderService {


    @Resource
    private SnowFlakeUtil snowFlakeUtil;


    @Override
    public LotteryOrder getByOrderId(String orderId) {
        QueryWrapper<LotteryOrder> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("order_id", orderId);
        LotteryOrder lotteryOrder = baseMapper.selectOne(queryWrapper);
        return lotteryOrder;
    }

    @Override
    public IPage<LotteryOrder> queryPage(ActivityOrderQueryReq req) {
        Page<LotteryOrder> page = new Page<>(req.getCurrentPage(), req.getSize());

        LambdaQueryChainWrapper<LotteryOrder> wrapper = new LambdaQueryChainWrapper<LotteryOrder>(baseMapper);

        Page<LotteryOrder> pageResult = wrapper.eq(req.getOrderId() != null, LotteryOrder::getOrderId, req.getOrderId())
                .eq(req.getOrderStatus() != null, LotteryOrder::getOrderStatus, req.getOrderStatus())
                .eq(req.getActivityId() != null, LotteryOrder::getActivityId, req.getActivityId())
                .eq(req.getUserId() != null, LotteryOrder::getUserId, req.getUserId())
                .eq(req.getMchId() != null, LotteryOrder::getMerchantId, req.getMchId())
                .gt(req.getCreateTimeBegin() != null, LotteryOrder::getCreateTime, req.getCreateTimeBegin())
                .lt(req.getCreateTimeEnd() != null, LotteryOrder::getCreateTime, req.getCreateTimeEnd())
                .page(page);

        return pageResult;
    }

}




