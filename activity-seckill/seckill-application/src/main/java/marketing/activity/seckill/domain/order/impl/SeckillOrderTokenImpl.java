package marketing.activity.seckill.domain.order.impl;

import marketing.activity.infrastructure.util.RedisUtil;
import marketing.activity.seckill.domain.order.ISeckillOrderToken;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class SeckillOrderTokenImpl implements ISeckillOrderToken {


    private String tokenCacheKey(Long activityId, String userId, String goodsId) {
        return "Seckill:Token:" + activityId + ":" + userId + ":" + goodsId;
    }

    @Resource
    private RedisUtil redisUtil;

    /**
     * 查询用户具备下单的Token
     *
     * @param activityId
     * @param userId
     * @param goodsId
     * @return
     */
    @Override
    public String queryToken(Long activityId, String userId, String goodsId) {
        String key = tokenCacheKey(activityId, userId, goodsId);
        String value = (String) redisUtil.get(key);
        return value;
    }

    /**
     * 保存用户的Token
     *
     * @param token
     * @param activityId
     * @param userId
     * @param goodsId
     * @return
     */
    @Override
    public boolean saveToken(String token, Long activityId, String userId, String goodsId) {
        return redisUtil.set(tokenCacheKey(activityId, userId, goodsId), token);
    }

}
