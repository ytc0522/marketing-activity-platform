package org.example.activity.repository.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.example.activity.repository.entity.UserTakeActivityRecord;
import org.example.activity.repository.service.UserTakeActivityRecordService;
import org.example.activity.repository.mapper.UserTakeActivityRecordMapper;
import org.springframework.stereotype.Service;

/**
* @author jack
* @description 针对表【user_take_activity_record】的数据库操作Service实现
* @createDate 2024-01-09 16:13:04
*/
@Service
public class UserTakeActivityRecordServiceImpl extends ServiceImpl<UserTakeActivityRecordMapper, UserTakeActivityRecord>
    implements UserTakeActivityRecordService{

}




