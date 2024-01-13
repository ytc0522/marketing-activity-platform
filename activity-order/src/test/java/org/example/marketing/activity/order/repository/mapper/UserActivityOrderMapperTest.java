package org.example.marketing.activity.order.repository.mapper;

import cn.hutool.core.lang.Snowflake;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.example.marketing.activity.order.repository.entity.UserActivityOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserActivityOrderMapperTest {

    @Resource
    private UserActivityOrderMapper orderMapper;

    @Test
    public void testInsert() {
        Snowflake snowflake = new Snowflake();
        UserActivityOrder order = new UserActivityOrder();
        String orderId = snowflake.nextIdStr();
        System.out.println("orderId = " + orderId);
        order.setOrderId(orderId);
        order.setUserId("10000");
        order.setActivityId(10001L);
        order.setAwardId("99999");
        order.setAwardName("优惠券");
        order.setAwardContent("xkksjjgkolell");

        int insert = orderMapper.insert(order);
        System.out.println("insert = " + insert);
    }

    /**
     * 如果查询SQL中没有分片键，那么会去查询所有的分片
     * 如果是分页查询，先查询前面所有的记录，如果有排序，先在数据库排好序。
     * 然后拿到数据后，再在内存中排序取出指定分页的数据
     */
    @Test
    public void test_queryPage() {
        Page<UserActivityOrder> page = new Page<>(2, 2);

        LambdaQueryWrapper<UserActivityOrder> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserActivityOrder::getUserId, "10000")
                .orderByDesc(UserActivityOrder::getAwardId);

        IPage<UserActivityOrder> pageResult
                = orderMapper.selectPage(page, queryWrapper);

        List<UserActivityOrder> records = pageResult.getRecords();
        System.out.println("records = " + records);

        System.out.println("pageResult.total = " + pageResult.getTotal());
    }


}