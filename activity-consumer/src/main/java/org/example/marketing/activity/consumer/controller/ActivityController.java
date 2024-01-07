package org.example.marketing.activity.consumer.controller;

import org.example.marketing.activity.consumer.service.ActivityService;
import org.example.marketing.common.ActionResult;
import org.example.marketing.common.req.activity.TakeActivityReq;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;


@RestController
@RequestMapping("/activity")
public class ActivityController {


    @Resource
    private ActivityService activityService;

    /**
     * 用户参加活动
     * @param req
     * @return
     */
    @RequestMapping("/take")
    public ActionResult take(@RequestBody TakeActivityReq req) {
        return activityService.takeActivity(req);
    }


}
