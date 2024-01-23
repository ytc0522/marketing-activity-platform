package marketing.activity.base.infrastructure.repository.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import marketing.activity.base.infrastructure.repository.entity.ActivityUserLog;
import marketing.activity.base.infrastructure.repository.mapper.ActivityUserLogMapper;
import marketing.activity.base.infrastructure.repository.service.ActivityUserLogService;
import org.springframework.stereotype.Service;

/**
 * @author jack
 * @description 针对表【activity_user_log】的数据库操作Service实现
 * @createDate 2024-01-22 22:01:34
 */
@Service
public class ActivityUserLogServiceImpl extends ServiceImpl<ActivityUserLogMapper, ActivityUserLog>
        implements ActivityUserLogService {

}




