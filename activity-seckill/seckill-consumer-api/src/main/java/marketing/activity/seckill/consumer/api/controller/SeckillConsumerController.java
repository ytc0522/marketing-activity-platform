package marketing.activity.seckill.consumer.api.controller;

import marketing.activity.seckill.ISeckillRpcService;
import marketing.activity.seckill.dto.ActionResult;
import marketing.activity.seckill.dto.SeckillReq;
import marketing.activity.seckill.dto.SeckillResult;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/activity/seckill")
public class SeckillConsumerController {


    @DubboReference
    private ISeckillRpcService seckillRpcService;

    /**
     * 秒杀接口
     * 异步处理
     *
     * @param req
     * @return
     */
    @PostMapping("/seckill")
    public ActionResult seckillAsync(@RequestBody SeckillReq req) {
        SeckillResult seckillResult = seckillRpcService.doSeckill(req);
        return ActionResult.success(seckillResult);
    }


    /**
     * 秒杀接口
     * 同步处理
     *
     * @param req
     * @return
     */
    @PostMapping("/seckill/sync")
    public ActionResult seckillSync(@RequestBody SeckillReq req) {
        return seckillRpcService.seckillSync(req);
    }


}
