package org.example.activity.admin.service.impl;


import org.example.activity.admin.service.ActivityService;
import org.example.activity.repository.entity.Activity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ActivityServiceImplTest {

    @Resource
    private ActivityService activityService;


    @Test
    public void test_insert() {

        Activity activity = new Activity();
        activity.setActivityName("抽奖活动");
        activity.setActivityDesc("这是一个抽奖活动");
        activity.setActivityId(10010L);
        activity.setBeginDateTime(new Date());
        activity.setActivityType(1);
        activity.setCreateTime(new Date());
        activity.setUpdateTime(new Date());
        activity.setState(5);
        activityService.save(activity);
    }




}