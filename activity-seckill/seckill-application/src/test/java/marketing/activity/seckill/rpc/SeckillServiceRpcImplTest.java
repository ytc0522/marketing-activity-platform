package marketing.activity.seckill.rpc;

import marketing.activity.seckill.ISeckillRpcService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SeckillServiceRpcImplTest {

    @Resource
    private ISeckillRpcService seckillRpcService;

    @Test
    public void preheat() {
        seckillRpcService.preheat(10010L);
    }

    @Test
    public void seckillAsync() {
    }

    @Test
    public void seckillSync() {
    }

    @Test
    public void seckillResult() {
    }
}