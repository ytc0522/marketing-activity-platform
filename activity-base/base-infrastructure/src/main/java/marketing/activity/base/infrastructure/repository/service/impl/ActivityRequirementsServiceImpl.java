package marketing.activity.base.infrastructure.repository.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import marketing.activity.base.infrastructure.repository.entity.ActivityRequirements;
import marketing.activity.base.infrastructure.repository.mapper.ActivityRequirementsMapper;
import marketing.activity.base.infrastructure.repository.service.ActivityRequirementsService;
import org.springframework.stereotype.Service;

/**
 * @author jack
 * @description 针对表【activity_requirements】的数据库操作Service实现
 * @createDate 2024-01-22 22:01:34
 */
@Service
public class ActivityRequirementsServiceImpl extends ServiceImpl<ActivityRequirementsMapper, ActivityRequirements>
        implements ActivityRequirementsService {

}




