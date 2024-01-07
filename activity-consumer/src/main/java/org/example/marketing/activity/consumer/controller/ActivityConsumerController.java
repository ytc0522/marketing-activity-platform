package org.example.marketing.activity.consumer.controller;

import org.apache.dubbo.config.annotation.DubboReference;
import org.example.marketing.common.ActionResult;
import org.example.marketing.lottery.rpc.ILotteryDraw;
import org.example.marketing.lottery.rpc.dto.AwardInfo;
import org.example.marketing.lottery.rpc.req.DrawReq;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping("/activity/consumer")
public class ActivityConsumerController {

    @DubboReference
    private ILotteryDraw lotteryDraw;

    @PostMapping("/lotteryDraw")
    public ActionResult lotteryDraw(@RequestBody DrawReq drawReq) {
        ActionResult<AwardInfo> drawResult = lotteryDraw.draw(drawReq);
        return ActionResult.success(drawResult);
    }


}
