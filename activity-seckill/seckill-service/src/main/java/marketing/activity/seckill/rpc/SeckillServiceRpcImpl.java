package marketing.activity.seckill.rpc;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import marketing.activity.seckill.ISeckillRpcService;
import marketing.activity.seckill.dto.ActionResult;
import marketing.activity.seckill.dto.SeckillReq;
import marketing.activity.seckill.dto.SeckillResult;
import marketing.activity.seckill.order.dto.SeckillOrderCreateReq;
import marketing.activity.seckill.order.rpc.ISeckillOrderRpcService;
import marketing.activity.seckill.repository.entity.SeckillGoods;
import marketing.activity.seckill.repository.mapper.SeckillActivityMapper;
import marketing.activity.seckill.repository.mapper.SeckillGoodsMapper;
import org.apache.dubbo.config.annotation.DubboReference;

import javax.annotation.Resource;

@Service
public class SeckillServiceRpcImpl implements ISeckillRpcService {

    @Resource
    private SeckillActivityMapper seckillActivityMapper;

    @Resource
    private SeckillGoodsMapper seckillGoodsMapper;

    @DubboReference
    private ISeckillOrderRpcService seckillOrderRpcService;


    @Override
    public void preheat(Long activityId) {

    }

    @Override
    public SeckillResult doSeckill(SeckillReq req) {
        // 根据活动ID找到秒杀商品
        Long activityId = req.getActivityId();

        // 从缓存中查找
        SeckillGoods seckillGoods = new LambdaQueryChainWrapper<SeckillGoods>(seckillGoodsMapper)
                .eq(SeckillGoods::getActivityId, activityId)
                .eq(SeckillGoods::getGoodsId, req.getGoodsId())
                .last("limit 1")
                .one();


        // 抢库存

        // 抢成功的话，发送MQ
        return null;
    }

    /**
     * 秒杀商品的同步处理
     *
     * @param req
     * @return
     */
    @Override
    public ActionResult seckillSync(SeckillReq req) {
        // 根据活动ID找到秒杀商品
        Long activityId = req.getActivityId();

        //
        SeckillGoods seckillGoods = new LambdaQueryChainWrapper<SeckillGoods>(seckillGoodsMapper)
                .eq(SeckillGoods::getActivityId, activityId)
                .eq(SeckillGoods::getGoodsId, req.getGoodsId())
                .last("limit 1")
                .one();

        if (seckillGoods == null) {
            return ActionResult.failure("goods not exist");
        }
        if (seckillGoods.getAvailableCount() == 0) {
            return ActionResult.failure("商品已抢完");
        }

        // 扣减库存
        int number = seckillGoodsMapper.deductStock(seckillGoods.getId(), seckillGoods.getAvailableCount());
        if (number == 0) {
            return ActionResult.failure("差一点点就抢到了～");
        }
        // 写订单
        SeckillOrderCreateReq createReq = new SeckillOrderCreateReq();
        createReq.setActivityId(activityId);
        createReq.setUserId(req.getUserId());
        createReq.setGoodsId(req.getGoodsId());
        String orderId = seckillOrderRpcService.createSeckillOrder(createReq);
        return ActionResult.success(orderId);
    }
}
