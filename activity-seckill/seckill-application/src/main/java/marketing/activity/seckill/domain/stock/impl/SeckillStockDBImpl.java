package marketing.activity.seckill.domain.stock.impl;

import marketing.activity.seckill.domain.stock.ISeckillActivityStock;
import org.springframework.stereotype.Component;

@Component
public class SeckillStockDBImpl implements ISeckillActivityStock {

    /**
     * 初始化库存
     *
     * @param activityId
     */
    @Override
    public void beforeActivityStart(Long activityId) {

    }

    /**
     * 扣减库存
     *
     * @param activityId
     * @param goodsId
     * @return
     */
    @Override
    public boolean deductStock(Long activityId, String goodsId) {
        return false;
    }

    /**
     * 查询库存
     *
     * @param activityId
     * @param goodsId
     * @return
     */
    @Override
    public Integer queryStock(Long activityId, String goodsId) {
        return null;
    }

    /**
     * 回滚库存
     *
     * @param activityId
     * @param goodsId
     * @return
     */
    @Override
    public boolean rollBack(Long activityId, String goodsId) {
        return false;
    }

    /**
     * @param activityId
     */
    @Override
    public void onActivityDone(Long activityId) {

    }
}
