package marketing.activity.seckill.order.rpc;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.Snowflake;
import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import marketing.activity.seckill.order.dto.SeckillOrderCreateReq;
import marketing.activity.seckill.order.dto.SeckillOrderDto;
import marketing.activity.seckill.order.dto.SeckillOrderQueryReq;
import marketing.activity.seckill.order.repository.entity.SeckillOrder;
import marketing.activity.seckill.order.repository.mapper.SeckillOrderMapper;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class SeckillOrderRpcServiceImpl implements ISeckillOrderRpcService {

    @Resource
    private SeckillOrderMapper seckillOrderMapper;

    private Snowflake snowflake = new Snowflake();


    /**
     * @param req
     */
    @Override
    public String createSeckillOrder(SeckillOrderCreateReq req) {
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
        seckillOrder.setGoodsId(req.getGoodsId());
        seckillOrder.setGoodsName("商品ID：" + req.getGoodsId());
        seckillOrder.setUserId(req.getUserId());

        seckillOrder.setCreateTime(new Date());
        seckillOrder.setUpdateTime(new Date());

        seckillOrderMapper.insert(seckillOrder);

        return seckillOrder.getOrderId();
    }

    /**
     * @param req
     * @return
     */
    @Override
    public List<SeckillOrderDto> query(SeckillOrderQueryReq req) {
        List<SeckillOrder> list = new LambdaQueryChainWrapper<SeckillOrder>(seckillOrderMapper)
                .eq(StringUtils.isNotBlank(req.getUserId()), SeckillOrder::getUserId, req.getUserId())
                .eq(StringUtils.isNotBlank(req.getOrderId()), SeckillOrder::getOrderId, req.getOrderId())
                .eq(req.getGoodsId() != null, SeckillOrder::getGoodsId, req.getGoodsId())
                .eq(req.getActivityId() != null, SeckillOrder::getActivityId, req.getActivityId())
                .list();

        List<SeckillOrderDto> seckillOrderDtos = BeanUtil.copyToList(list, SeckillOrderDto.class);
        return seckillOrderDtos;
    }
}
