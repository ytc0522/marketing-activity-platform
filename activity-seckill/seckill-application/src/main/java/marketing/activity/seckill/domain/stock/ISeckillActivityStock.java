package marketing.activity.seckill.domain.stock;

/**
 * 秒杀商品库存存储
 */
public interface ISeckillActivityStock {


    /**
     * 初始化库存
     *
     * @param activityId
     */
    void beforeActivityStart(Long activityId);

    /**
     * 扣减库存
     *
     * @param activityId
     * @param goodsId
     * @return
     */
    boolean deductStock(Long activityId, String goodsId);

    /**
     * 查询库存
     *
     * @param activityId
     * @param goodsId
     * @return
     */
    Integer queryStock(Long activityId, String goodsId);


    /**
     * 回滚库存
     *
     * @param activityId
     * @param goodsId
     * @return
     */
    boolean rollBack(Long activityId, String goodsId);


    void onActivityDone(Long activityId);

}
