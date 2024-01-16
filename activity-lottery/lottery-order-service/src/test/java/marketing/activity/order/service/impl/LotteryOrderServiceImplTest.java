package marketing.activity.order.service.impl;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import marketing.activity.order.dto.req.ActivityOrderQueryReq;
import marketing.activity.order.repository.entity.LotteryOrder;
import marketing.activity.order.service.LotteryOrderService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LotteryOrderServiceImplTest {


    @Resource
    private LotteryOrderService activityOrderService;

    @Test
    public void getByOrderId() {
        LotteryOrder lotteryOrder = activityOrderService.getByOrderId("1745815005089697792");
        System.out.println("userActivityOrder = " + lotteryOrder);
    }


    @Test
    public void test_queryPage() {

        DateTime date = DateUtil.parse("2024-01-13", DatePattern.NORM_DATE_FORMAT);

        ActivityOrderQueryReq req = ActivityOrderQueryReq.builder()
                .userId("10000")
                .createTimeBegin(date)
                .build();

        req.setCurrentPage(1);
        req.setSize(10);

        long currentTimeMillis = System.currentTimeMillis();
        IPage<LotteryOrder> result = activityOrderService.queryPage(req);
        long now = System.currentTimeMillis();

        long diff = now - currentTimeMillis;

        System.out.println("diff = " + diff);


        long total = result.getTotal();
        System.out.println("total = " + total);

        for (LotteryOrder record : result.getRecords()) {
            System.out.println("record = " + record);
        }
    }
}