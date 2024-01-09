package org.example.marketing.activity.consumer.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.example.activity.repository.entity.UserActivityOrder;
import org.example.activity.repository.mapper.UserActivityOrderMapper;
import org.example.marketing.activity.consumer.service.UserActivityOrderService;
import org.springframework.stereotype.Service;

/**
* @author jack
* @description 针对表【user_activity_order】的数据库操作Service实现
* @createDate 2024-01-09 17:14:29
*/
@Service
public class UserActivityOrderServiceImpl extends ServiceImpl<UserActivityOrderMapper, UserActivityOrder>
    implements UserActivityOrderService {

}




