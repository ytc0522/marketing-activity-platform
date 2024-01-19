package marketing.activity.seckill.service;

import com.baomidou.mybatisplus.extension.service.IService;
import marketing.activity.seckill.dto.ActionResult;
import marketing.activity.seckill.dto.SeckillReq;
import marketing.activity.seckill.infrastructure.repository.entity.SeckillActivity;

/**
 * @author jack
 * @description 针对表【seckill_activity】的数据库操作Service
 * @createDate 2024-01-17 15:49:41
 */
public interface SeckillActivityService extends IService<SeckillActivity> {


    public ActionResult seckillSync(SeckillReq req);

    void preHeat(Long activityId);

    ActionResult seckillAsync(SeckillReq req);

    ActionResult seckillResult(SeckillReq req, String token);
}
