package marketing.activity.backend.controller;

import marketing.activity.lottery.rpc.ILotteryBackendRpcService;
import marketing.activity.lottery.rpc.dto.LotteryActivityDto;
import marketing.activity.lottery.rpc.dto.PageData;
import marketing.activity.lottery.rpc.req.ActivityQueryReq;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * * @Author: jack
 * * @Date    2024/1/28 20:26
 * * @Description 相信坚持的力量！
 **/
@RequestMapping("/")
@RestController
public class ActivityController {

    @DubboReference
    private ILotteryBackendRpcService backendRpcService;


    @PostMapping("/query")
    public PageData queryActivity(@RequestBody ActivityQueryReq req) {
        PageData<LotteryActivityDto> data = backendRpcService.queryPage(req);
        return data;
    }


}
