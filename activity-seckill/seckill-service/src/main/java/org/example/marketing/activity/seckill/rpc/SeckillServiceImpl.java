package org.example.marketing.activity.seckill.rpc;

import com.alibaba.dubbo.config.annotation.Service;
import org.example.marketing.activity.seckill.ISeckillService;
import org.example.marketing.activity.seckill.dto.SeckillReq;
import org.example.marketing.activity.seckill.dto.SeckillResult;

@Service
public class SeckillServiceImpl implements ISeckillService {


    @Override
    public void preheat(Long activityId) {

    }

    @Override
    public SeckillResult doSeckill(SeckillReq req) {

        // 根据活动ID找到秒杀商品

        // 抢库存

        // 抢成功的话，发送MQ

        //
        return null;
    }
}
