package marketing.activity.lottery.admin.api.controller;

import marketing.activity.lottery.rpc.ILotteryRpcService;
import marketing.activity.lottery.rpc.dto.ActionResult;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/lottery/admin")
public class LotteryAdminController {


    @DubboReference
    private ILotteryRpcService lotteryRpcService;


    @GetMapping("/prepare")
    public ActionResult prepare(Long activityId) {
        boolean prepare = lotteryRpcService.prepare(activityId);
        return ActionResult.success();
    }

}
