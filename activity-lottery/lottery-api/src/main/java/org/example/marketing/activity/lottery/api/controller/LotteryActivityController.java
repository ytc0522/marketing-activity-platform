package org.example.marketing.activity.lottery.api.controller;

import org.apache.dubbo.config.annotation.DubboReference;
import org.example.marketing.common.ActionResult;
import org.example.marketing.lottery.rpc.ILotteryRpcService;
import org.example.marketing.lottery.rpc.req.LotteryDrawReq;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/activity/lottery")
@RestController
public class LotteryActivityController {


    @DubboReference
    private ILotteryRpcService lotteryDraw;


    @PostMapping("/draw")
    public ActionResult drawLottery(LotteryDrawReq lotteryDrawReq) {
        return ActionResult.success(lotteryDraw.draw(lotteryDrawReq));
    }


}
