package org.example.activity.admin.controller;

import org.example.activity.admin.service.ActivityService;
import org.example.activity.repository.entity.Activity;
import org.example.marketing.common.ActionResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/activity/admin")
public class ActivityAdminController {


    @Resource
    private ActivityService activityService;

    @GetMapping
    public ActionResult queryAll() {
        List<Activity> list = activityService.list();
        return ActionResult.success(list);
    }


}
