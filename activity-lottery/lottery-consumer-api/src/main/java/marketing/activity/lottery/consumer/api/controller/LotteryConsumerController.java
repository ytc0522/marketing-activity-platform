package marketing.activity.lottery.consumer.api.controller;

import marketing.lottery.rpc.ILotteryRpcService;
import marketing.lottery.rpc.dto.ActionResult;
import marketing.lottery.rpc.req.LotteryDrawReq;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/activity/lottery")
@RestController
public class LotteryConsumerController {


    @DubboReference
    private ILotteryRpcService lotteryDraw;


    @PostMapping("/draw")
    public ActionResult drawLottery(@RequestBody LotteryDrawReq lotteryDrawReq) {
        return ActionResult.success(lotteryDraw.draw(lotteryDrawReq));
    }


}
