package org.example.marketing.activity.consumer.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.example.activity.repository.entity.Award;
import org.example.activity.repository.mapper.AwardMapper;
import org.example.marketing.activity.consumer.service.AwardService;
import org.springframework.stereotype.Service;

/**
* @author jack
* @description 针对表【award(奖品配置)】的数据库操作Service实现
* @createDate 2024-01-07 00:46:19
*/
@Service
public class AwardServiceImpl extends ServiceImpl<AwardMapper, Award>
    implements AwardService {

}




