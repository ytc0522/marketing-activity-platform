package marketing.activity.seckill.order.service;

import com.baomidou.mybatisplus.extension.service.IService;
import marketing.activity.seckill.order.dto.SeckillOrderCreateReq;
import marketing.activity.seckill.order.repository.entity.SeckillOrder;

/**
 * @author jack
 * @description 针对表【seckill_order_0】的数据库操作Service
 * @createDate 2024-01-17 16:07:05
 */
public interface SeckillOrderService extends IService<SeckillOrder> {


    boolean createSeckillOrder(SeckillOrderCreateReq req);

}
