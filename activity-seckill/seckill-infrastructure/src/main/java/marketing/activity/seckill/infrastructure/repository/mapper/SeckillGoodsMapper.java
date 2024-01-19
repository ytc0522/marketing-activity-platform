package marketing.activity.seckill.infrastructure.repository.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import marketing.activity.seckill.infrastructure.repository.entity.SeckillGoods;
import org.apache.ibatis.annotations.Param;

/**
 * @author jack
 * @description 针对表【seckill_goods】的数据库操作Mapper
 * @createDate 2024-01-17 16:36:27
 * @Entity marketing.activity.seckill.repository.entity.SeckillGoods
 */
public interface SeckillGoodsMapper extends BaseMapper<SeckillGoods> {

    int deductStock(@Param("id") long id, @Param("beforeStock") int beforeStock);

}




