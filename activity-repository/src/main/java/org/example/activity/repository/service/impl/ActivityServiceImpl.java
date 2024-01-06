package org.example.activity.repository.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.example.activity.repository.activity.Activity;
import org.example.activity.repository.service.ActivityService;
import org.example.activity.repository.mapper.ActivityMapper;
import org.springframework.stereotype.Service;

/**
* @author jack
* @description 针对表【activity(活动配置)】的数据库操作Service实现
* @createDate 2024-01-07 01:13:48
*/
@Service
public class ActivityServiceImpl extends ServiceImpl<ActivityMapper, Activity>
    implements ActivityService{

}




