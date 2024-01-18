package marketing.activity.seckill.service.impl;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import marketing.activity.seckill.domain.order.ISeckillOrderFacade;
import marketing.activity.seckill.domain.order.ISeckillOrderToken;
import marketing.activity.seckill.domain.stock.impl.SeckillActivityStockRedisImpl;
import marketing.activity.seckill.domain.stock.impl.SeckillStockDBImpl;
import marketing.activity.seckill.dto.ActionResult;
import marketing.activity.seckill.dto.SeckillReq;
import marketing.activity.seckill.mq.producer.EventProducer;
import marketing.activity.seckill.order.dto.SeckillOrderCreateReq;
import marketing.activity.seckill.order.dto.SeckillOrderDto;
import marketing.activity.seckill.order.dto.SeckillOrderQueryReq;
import marketing.activity.seckill.order.dto.SeckillWinGoods;
import marketing.activity.seckill.order.mq.Event;
import marketing.activity.seckill.repository.entity.SeckillActivity;
import marketing.activity.seckill.repository.entity.SeckillGoods;
import marketing.activity.seckill.repository.mapper.SeckillActivityMapper;
import marketing.activity.seckill.service.SeckillActivityService;
import marketing.activity.seckill.service.SeckillGoodsService;
import marketing.activity.seckill.util.JwtUtil;
import org.apache.commons.lang3.StringUtils;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author jack
 * @description 针对表【seckill_activity】的数据库操作Service实现
 * @createDate 2024-01-17 15:49:41
 */
@Service
public class SeckillActivityServiceImpl extends ServiceImpl<SeckillActivityMapper, SeckillActivity>
        implements SeckillActivityService {

    @Resource
    private RedissonClient redissonClient;

    @Resource
    private ISeckillOrderFacade seckillOrderFacade;

    @Resource
    private ISeckillOrderToken orderToken;

    @Resource
    private SeckillActivityStockRedisImpl seckillActivityRedisStock;

    @Resource
    private SeckillStockDBImpl seckillDbStock;

    @Resource
    private SeckillGoodsService goodsService;


    @Resource
    private EventProducer eventProducer;


    public ActionResult seckillSync(SeckillReq req) {

        RLock lock = redissonClient.getLock("seckill_sync_lock");

        lock.lock(); // 阻塞等待

        // 根据活动ID找到秒杀商品
        String orderId = null;
        try {
            Long activityId = req.getActivityId();

            SeckillGoods seckillGoods = goodsService.lambdaQuery()
                    .eq(SeckillGoods::getActivityId, activityId)
                    .eq(SeckillGoods::getGoodsId, req.getGoodsId())
                    .last("limit 1")
                    .one();

            if (seckillGoods == null) {
                return ActionResult.failure("goods not exist");
            }

            // 判断用户是否已经抢到了,校验数量
            SeckillOrderQueryReq queryReq = SeckillOrderQueryReq.builder()
                    .userId(req.getUserId())
                    .activityId(activityId)
                    .goodsId(req.getGoodsId())
                    .build();
            List<SeckillOrderDto> list = seckillOrderFacade.queryFromDB(queryReq);
            if (CollectionUtils.isNotEmpty(list)) {
                return ActionResult.failure("每人最多一件！");
            }

            // 扣减库存
            boolean success = seckillDbStock.deductStock(activityId, req.getGoodsId());

            if (success) {
                return ActionResult.failure("差一点点就抢到了～～");
            }

            // 写订单
            SeckillOrderCreateReq createReq = new SeckillOrderCreateReq();
            createReq.setActivityId(activityId);
            createReq.setUserId(req.getUserId());
            createReq.setGoodsId(req.getGoodsId());
            orderId = seckillOrderFacade.createSeckillOrder(createReq);
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
        }
        return ActionResult.success(orderId);
    }

    /**
     *
     */
    @Override
    public void preHeat() {

    }

    /**
     * @param req
     * @return
     */
    @Override
    public ActionResult seckillAsync(SeckillReq req) {

        // 根据活动ID找到秒杀商品
        Long activityId = req.getActivityId();
        // 省略校验活动时间的代码
        // 从缓存中查找
        SeckillGoods seckillGoods = goodsService.querySeckillGoods(activityId, req.getGoodsId());
        if (seckillGoods == null) {
            return ActionResult.failure("活动无该商品");
        }
        // redis 中扣减库存
        boolean success = seckillActivityRedisStock.deductStock(activityId, req.getGoodsId());
        if (!success) {
            return ActionResult.failure("差一点点就抢购成功了～");
        }

        Integer availableStock = seckillActivityRedisStock.queryStock(activityId, req.getGoodsId());

        // 发送消息，目的是为了将redis中库存同步给数据库中
        publishSeckillWinGoodsMsq(req, availableStock);

        return ActionResult.success();
    }

    /**
     * 发送MQ消息需要保证消息不会丢失
     *
     * @param req
     */
    private void publishSeckillWinGoodsMsq(SeckillReq req, Integer availableStock) {
        // 发送抢购成功消息
        SeckillWinGoods seckillWinGoods = new SeckillWinGoods();
        seckillWinGoods.setActivityId(req.getActivityId());
        seckillWinGoods.setGoodsId(req.getGoodsId());
        seckillWinGoods.setUserId(req.getUserId());
        seckillWinGoods.setAvailableStock(availableStock);

        Event<SeckillWinGoods> event = new Event<>();
        event.setBody(seckillWinGoods);
        event.setType(Event.Type.SECKILL_USER_WIN_GOODS);
        eventProducer.publish(event);
    }

    /**
     * 根据token查询用户秒杀订单是否已经创建
     *
     * @param token
     * @return
     */
    @Override
    public ActionResult querySeckillOrderByToken(String token) {

        return null;
    }

    /**
     * @param req
     * @param token
     * @return
     */
    @Override
    public ActionResult seckillResult(SeckillReq req, String token) {
        // 校验token
        boolean validate = JwtUtil.validate(token);
        // 省略判断token和req是否一致，如果不一致，返回失败

        String cacheToken = orderToken.queryToken(req.getActivityId(), req.getUserId(), req.getGoodsId());
        if (StringUtils.isNotEmpty(cacheToken) && JwtUtil.validate(cacheToken)) {
            // 要查订单,应该先从缓存中查询

            //seckillOrderFacade.queryFromDB()
        }
        return ActionResult.failure("没有抢到商品～～");
    }


    private String createToken(Long activityId, String userId, String goodsId) {
        Map<String, Object> map = new HashMap<>();
        map.put("activityId", activityId);
        map.put("goodsId", goodsId);
        map.put("userId", userId);
        return JwtUtil.createToken(map);
    }


}




