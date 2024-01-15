package org.example.activity.repository.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.example.activity.repository.entity.Activity;

/**
* @author jack
* @description 针对表【activity(活动配置)】的数据库操作Mapper
* @createDate 2024-01-07 01:13:48
* @Entity org.example.activity.repository.activity.Activity
*/
@Mapper
public interface ActivityMapper extends BaseMapper<Activity> {

}




