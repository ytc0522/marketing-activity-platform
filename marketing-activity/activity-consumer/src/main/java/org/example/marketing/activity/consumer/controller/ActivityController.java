package org.example.marketing.activity.consumer.controller;

import marketing.activity.lottery.rpc.dto.ActionResult;
import org.example.marketing.activity.consumer.model.dto.TakeActivityReq;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/activity")
public class ActivityController {


    /**
     * 用户参加活动
     */

    @PostMapping("/take")
    public ActionResult takeActivity(@RequestBody TakeActivityReq req) {
        return null;
    }


}
