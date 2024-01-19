package marketing.activity.seckill.consumer.api.controller;

import lombok.extern.slf4j.Slf4j;
import marketing.activity.seckill.ISeckillRpcService;
import marketing.activity.seckill.dto.ActionResult;
import marketing.activity.seckill.dto.SeckillReq;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.*;

@Slf4j
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
        return ActionResult.success(seckillRpcService.seckillAsync(req));
    }

    @PostMapping("/result")
    public ActionResult seckillResult(@RequestBody SeckillReq req,
                                      @RequestParam("token") String token) {
        return ActionResult.success(seckillRpcService.seckillResult(req, token));
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
        ActionResult actionResult = ActionResult.failure();
        try {
            actionResult = seckillRpcService.seckillSync(req);
        } catch (Exception e) {
            log.error("seckillSync error", e);
        }
        return actionResult;
    }


}
