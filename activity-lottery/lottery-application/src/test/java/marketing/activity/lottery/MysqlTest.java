package marketing.activity.lottery;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import marketing.activity.infrastructure.util.SnowflakeUtil;
import marketing.activity.lottery.infrastructure.repository.entity.LotteryActivity;
import marketing.activity.lottery.infrastructure.repository.mapper.LotteryActivityMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * * @Author: jack
 * * @Date    2024/1/28 21:47
 * * @Description 相信坚持的力量！
 **/
@RunWith(SpringRunner.class)
@SpringBootTest
public class MysqlTest {

    @Resource
    private LotteryActivityMapper mapper;


    @Test
    public void slowSqlTest() throws InterruptedException {

        Thread thread = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                LotteryActivity lotteryActivity = new LotteryActivity();
                lotteryActivity.setActivityId(SnowflakeUtil.nextId());
                lotteryActivity.setActivityName("test-name");
                lotteryActivity.setCreateTime(new Date());
                lotteryActivity.setStatus(0);
                mapper.insert(lotteryActivity);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        Thread writeThread = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                List<LotteryActivity> list = mapper.selectList(new QueryWrapper<>());
                System.out.println("当前Thread：" + Thread.currentThread().getName());
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(300);

        for (int i = 0; i < 300; i++) {
            executorService.scheduleAtFixedRate(writeThread, 1, 1, TimeUnit.SECONDS);
            executorService.scheduleAtFixedRate(thread, 1, 1, TimeUnit.SECONDS);

            TimeUnit.SECONDS.sleep(10);
        }

        TimeUnit.HOURS.sleep(100000);

    }

}
