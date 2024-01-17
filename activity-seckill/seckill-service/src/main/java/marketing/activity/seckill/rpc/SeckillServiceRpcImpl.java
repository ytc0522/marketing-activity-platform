package marketing.activity.seckill.rpc;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import marketing.activity.seckill.ISeckillRpcService;
import marketing.activity.seckill.dto.ActionResult;
import marketing.activity.seckill.dto.SeckillReq;
import marketing.activity.seckill.dto.SeckillResult;
import marketing.activity.seckill.order.dto.SeckillOrderCreateReq;
import marketing.activity.seckill.order.dto.SeckillOrderDto;
import marketing.activity.seckill.order.dto.SeckillOrderQueryReq;
import marketing.activity.seckill.order.rpc.ISeckillOrderRpcService;
import marketing.activity.seckill.repository.entity.SeckillGoods;
import marketing.activity.seckill.repository.mapper.SeckillActivityMapper;
import marketing.activity.seckill.repository.mapper.SeckillGoodsMapper;
import org.apache.dubbo.config.annotation.DubboReference;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
public class SeckillServiceRpcImpl implements ISeckillRpcService {

    @Resource
    private SeckillActivityMapper seckillActivityMapper;

    @Resource
    private SeckillGoodsMapper seckillGoodsMapper;

    @DubboReference
    private ISeckillOrderRpcService seckillOrderRpcService;

    @Resource
    private RedissonClient redissonClient;


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
     * 秒杀商品的同步处理, 使用分布式锁 + 同步写DB
     *
     * @param req
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ActionResult seckillSync(SeckillReq req) {

        RLock lock = redissonClient.getLock("seckill_sync_lock");

        lock.lock(); // 阻塞等待

        // 根据活动ID找到秒杀商品
        String orderId = null;
        try {
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

            // 判断用户是否已经抢到了,校验数量
            SeckillOrderQueryReq queryReq = SeckillOrderQueryReq.builder().userId(req.getUserId()).activityId(activityId).goodsId(req.getGoodsId()).build();
            List<SeckillOrderDto> list = seckillOrderRpcService.query(queryReq);
            if (CollectionUtils.isNotEmpty(list)) {
                return ActionResult.failure("每人最多一件！");
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
            orderId = seckillOrderRpcService.createSeckillOrder(createReq);
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
        }
        return ActionResult.success(orderId);
    }
}
