package org.example.marketing.activity.order.repository.mapper;

import cn.hutool.core.lang.Snowflake;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.example.marketing.activity.order.repository.entity.LotteryOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LotteryOrderMapperTest {

    @Resource
    private LotteryOrderMapper orderMapper;

    @Test
    public void testInsert() {
        Snowflake snowflake = new Snowflake();
        LotteryOrder order = new LotteryOrder();
        String orderId = snowflake.nextIdStr();
        System.out.println("orderId = " + orderId);
        order.setOrderId(orderId);
        order.setUserId("10000");
        order.setActivityId(10001L);
        order.setAwardId("99999");
        order.setAwardName("优惠券");
        order.setCreateTime(new Date());
        order.setUpdateTime(new Date());

        int insert = orderMapper.insert(order);
        System.out.println("insert = " + insert);

        QueryWrapper<LotteryOrder> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("order_id", orderId);
        LotteryOrder lotteryOrder = orderMapper.selectOne(queryWrapper);
        System.out.println("userActivityOrder = " + lotteryOrder);
    }

    /**
     * 如果查询SQL中没有分片键，那么会去查询所有的分片
     * 如果是分页查询，先查询前面所有的记录，如果有排序，先在数据库排好序。
     * 然后拿到数据后，再在内存中排序取出指定分页的数据
     */
    @Test
    public void test_queryPage() {
        Page<LotteryOrder> page = new Page<>(2, 2);

        LambdaQueryWrapper<LotteryOrder> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(LotteryOrder::getUserId, "10000")
                .orderByDesc(LotteryOrder::getAwardId);

        IPage<LotteryOrder> pageResult
                = orderMapper.selectPage(page, queryWrapper);

        List<LotteryOrder> records = pageResult.getRecords();
        System.out.println("records = " + records);

        System.out.println("pageResult.total = " + pageResult.getTotal());
    }


}