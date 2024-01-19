package marketing.activity.seckill.domain.order;

public interface ISeckillOrderToken {


    /**
     * 查询用户具备下单的Token
     *
     * @param activityId
     * @param userId
     * @param goodsId
     * @return
     */
    String queryToken(Long activityId, String userId, String goodsId);


    /**
     * 保存用户的Token
     *
     * @return
     */
    boolean saveToken(String token, Long activityId, String userId, String goodsId);
}
