package marketing.activity.seckill.service;

import com.baomidou.mybatisplus.extension.service.IService;
import marketing.activity.seckill.dto.ActionResult;
import marketing.activity.seckill.dto.SeckillReq;
import marketing.activity.seckill.repository.entity.SeckillActivity;

/**
 * @author jack
 * @description 针对表【seckill_activity】的数据库操作Service
 * @createDate 2024-01-17 15:49:41
 */
public interface SeckillActivityService extends IService<SeckillActivity> {


    public ActionResult seckillSync(SeckillReq req);

    void preHeat();

    ActionResult seckillAsync(SeckillReq req);

    /**
     * 根据token查询用户秒杀订单是否已经创建
     *
     * @param token
     * @return
     */
    ActionResult querySeckillOrderByToken(String token);

    ActionResult seckillResult(SeckillReq req, String token);
}
