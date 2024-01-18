package marketing.activity.seckill.rpc;

import com.alibaba.dubbo.config.annotation.Service;
import marketing.activity.seckill.ISeckillRpcService;
import marketing.activity.seckill.dto.ActionResult;
import marketing.activity.seckill.dto.SeckillReq;
import marketing.activity.seckill.order.rpc.ISeckillOrderRpcService;
import marketing.activity.seckill.service.SeckillActivityService;
import marketing.activity.seckill.service.SeckillGoodsService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
public class SeckillServiceRpcImpl implements ISeckillRpcService {


    @Resource
    private SeckillActivityService seckillActivityService;

    @Resource
    private SeckillGoodsService seckillGoodsService;

    @DubboReference
    private ISeckillOrderRpcService seckillOrderRpcService;


    @Override
    public void preheat(Long activityId) {
        seckillActivityService.preHeat();
    }

    @Override
    public ActionResult seckillAsync(SeckillReq req) {
        return seckillActivityService.seckillAsync(req);
    }

    /**
     * 秒杀商品的同步处理, 使用分布式锁 + 同步写DB
     * 注意：库存和订单不在同一个数据库实例上，如何保证分布式事务一致性？
     *
     * @param req
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ActionResult seckillSync(SeckillReq req) {
        return seckillActivityService.seckillSync(req);
    }

    /**
     * 获取秒杀结果
     *
     * @param req
     * @param token
     * @return
     */
    @Override
    public ActionResult seckillResult(SeckillReq req, String token) {
        return seckillActivityService.seckillResult(req, token);
    }
}
