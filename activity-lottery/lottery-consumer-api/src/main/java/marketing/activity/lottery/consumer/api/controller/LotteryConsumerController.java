package marketing.activity.lottery.consumer.api.controller;

import marketing.lottery.rpc.ILotteryRpcService;
import marketing.lottery.rpc.dto.ActionResult;
import marketing.lottery.rpc.req.LotteryDrawReq;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/activity/lottery")
@RestController
public class LotteryConsumerController {


    @DubboReference
    private ILotteryRpcService lotteryDraw;


    @GetMapping("/hello")
    public ActionResult hello() {
        return ActionResult.success("Hi ,lottery ");
    }

    @PostMapping("/draw")
    public ActionResult drawLottery(@RequestBody LotteryDrawReq lotteryDrawReq) {
        return lotteryDraw.draw(lotteryDrawReq);
    }


}
