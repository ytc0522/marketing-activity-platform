package marketing.activity.seckill.order.service.impl;

import cn.hutool.core.lang.Snowflake;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import marketing.activity.seckill.order.dto.SeckillOrderCreateReq;
import marketing.activity.seckill.order.repository.entity.SeckillOrder;
import marketing.activity.seckill.order.repository.mapper.SeckillOrderMapper;
import marketing.activity.seckill.order.service.SeckillOrderService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author jack
 * @description 针对表【seckill_order_0】的数据库操作Service实现
 * @createDate 2024-01-17 16:07:05
 */
@Service
public class SeckillOrderServiceImpl extends ServiceImpl<SeckillOrderMapper, SeckillOrder>
        implements SeckillOrderService {


    private Snowflake snowflake = new Snowflake();

    public boolean createSeckillOrder(SeckillOrderCreateReq req) {
        SeckillOrder seckillOrder = new SeckillOrder();
        seckillOrder.setOrderStatus("0");
        seckillOrder.setActivityId(req.getActivityId());
        if (StringUtils.isEmpty(req.getOrderId())) {
            seckillOrder.setOrderId(snowflake.nextIdStr());
        } else {
            seckillOrder.setOrderId(req.getOrderId());
        }
        // 商户ID 这里模拟一些商户
        seckillOrder.setMerchantId(snowflake.nextId());
        seckillOrder.setGoodsId(Long.parseLong(req.getGoodsId()));
        seckillOrder.setGoodsName("商品ID：" + req.getGoodsId());
        seckillOrder.setUserId(req.getUserId());

        seckillOrder.setCreateTime(new Date());
        seckillOrder.setUpdateTime(new Date());

        return this.save(seckillOrder);
    }

}




