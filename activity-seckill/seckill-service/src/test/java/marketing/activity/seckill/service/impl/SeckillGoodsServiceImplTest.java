package marketing.activity.seckill.service.impl;

import marketing.activity.seckill.repository.entity.SeckillGoods;
import marketing.activity.seckill.service.SeckillGoodsService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SeckillGoodsServiceImplTest {


    @Resource
    private SeckillGoodsService service;

    @Test
    public void queryByActivityId() {

        List<SeckillGoods> seckillGoods = service.queryByActivityId(10010L);
        System.out.println("seckillGoods = " + seckillGoods);
    }
}