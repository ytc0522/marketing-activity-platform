package org.example.marketing.lottery.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.Snowflake;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import marketing.activity.lottery.order.ILotteryOrderRpcService;
import marketing.activity.lottery.order.dto.LotteryWinAwardDto;
import org.apache.dubbo.config.annotation.DubboReference;
import org.example.marketing.lottery.domain.pool.ILotteryAwardPool;
import org.example.marketing.lottery.domain.stock.ILotteryAwardStockStorage;
import org.example.marketing.lottery.repository.entity.LotteryActivity;
import org.example.marketing.lottery.repository.entity.LotteryAward;
import org.example.marketing.lottery.repository.mapper.LotteryActivityMapper;
import org.example.marketing.lottery.repository.util.RedisUtil;
import org.example.marketing.lottery.rpc.ILotteryRpcService;
import org.example.marketing.lottery.rpc.constants.Constants;
import org.example.marketing.lottery.rpc.dto.LotteryActivityDto;
import org.example.marketing.lottery.rpc.dto.LotteryActivityRich;
import org.example.marketing.lottery.rpc.dto.LotteryAwardDto;
import org.example.marketing.lottery.rpc.req.LotteryDrawReq;
import org.example.marketing.lottery.service.LotteryActivityService;
import org.example.marketing.lottery.service.LotteryAwardService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author jack
 * @description 针对表【lottery】的数据库操作Service实现
 * @createDate 2024-01-07 20:39:51
 */
@Slf4j
@Service
public class LotteryActivityServiceImpl extends ServiceImpl<LotteryActivityMapper, LotteryActivity>
        implements LotteryActivityService, ILotteryRpcService {


    @Resource
    private LotteryAwardService lotteryAwardService;

    @Resource
    private RedisUtil redisUtil;

    private static String LOTTERY_KEY = "LOTTERY_KEY:";

    @Resource
    private Map<Integer, ILotteryAwardPool> lotteryAwardPoolMap;

    @DubboReference
    private ILotteryOrderRpcService lotteryOrderRpcService;

    @Resource
    private ILotteryAwardStockStorage stockStorage;

    private Snowflake snowflake = new Snowflake();

    @Override
    public LotteryActivityRich refreshCache(Long lotteryId) {
        String lotteryCacheKey = LOTTERY_KEY + lotteryId;
        redisUtil.del(lotteryCacheKey);
        return getFromCache(lotteryId);
    }

    @Override
    public LotteryActivityRich getFromCache(Long lotteryActivityId) {
        String lotteryCacheKey = LOTTERY_KEY + lotteryActivityId;

        LotteryActivityRich lotteryActivityRich = (LotteryActivityRich) redisUtil.get(lotteryCacheKey);

        if (lotteryActivityRich != null) {
            return lotteryActivityRich;
        }

        LotteryActivity lotteryActivity = this.getById(lotteryActivityId);
        if (lotteryActivity == null) {
            return null;
        }
        List<LotteryAward> detailList = lotteryAwardService.lambdaQuery().eq(LotteryAward::getActivityId, lotteryActivityId)
                .list();

        LotteryActivityRich newLotteryActivityRich = new LotteryActivityRich();
        LotteryActivityDto lotteryActivityDto = new LotteryActivityDto();
        BeanUtil.copyProperties(lotteryActivity, lotteryActivityDto);

        List<LotteryAwardDto> lotteryAwardDtos = BeanUtil.copyToList(detailList, LotteryAwardDto.class);

        newLotteryActivityRich.setLotteryActivity(lotteryActivityDto);
        newLotteryActivityRich.setLotteryAwardlDtoList(lotteryAwardDtos);

        redisUtil.set(lotteryCacheKey, newLotteryActivityRich, 3600 * 3);
        return newLotteryActivityRich;

    }
//
//    @Override
//    public boolean deductStock(Long lotteryActivityId, String awardId) {
//
//        UpdateWrapper<LotteryAward> updateWrapper = new UpdateWrapper<>();
//        updateWrapper.setSql("award_surplus_count = award_surplus_count - 1");
//        updateWrapper.eq("lottery_id", lotteryActivityId);
//        updateWrapper.eq("award_id", awardId);
//        updateWrapper.gt("award_surplus_count", 0);
//
//        return lotteryAwardService.update(updateWrapper);
//    }

//    @Override
//    public boolean updateStockCount(Long lotteryActivityId, String awardId, Long surplusCount) {
//
//        boolean update = lotteryAwardService.lambdaUpdate().eq(LotteryAward::getActivityId, lotteryActivityId)
//                .eq(LotteryAward::getAwardId, awardId)
//                .set(LotteryAward::getAvailableCount, surplusCount).update();
//        return update;
//    }


    @Override
    public boolean prepare(Long activityId) {
        LotteryActivityRich lotteryActivityRich = this.refreshCache(activityId);
        ILotteryAwardPool lotteryAwardPool = lotteryAwardPoolMap.get(lotteryActivityRich.getLotteryActivity().getProbabilityType());
        lotteryAwardPool.refreshPool(lotteryActivityRich);
        stockStorage.refreshStock(activityId);
        return true;
    }

    @Override
    public LotteryWinAwardDto draw(LotteryDrawReq req) {

        LotteryWinAwardDto lotteryAward = new LotteryWinAwardDto();

        // 从缓存中获取 抽奖相关信息
        LotteryActivityRich lotteryActivityRich = this.getFromCache(req.getActivityId());

        LotteryActivityDto lotteryActivity = lotteryActivityRich.getLotteryActivity();

        Long activityId = lotteryActivity.getActivityId();

        Integer probabilityType = lotteryActivity.getProbabilityType();
        if (probabilityType == null) probabilityType = 0;

        ILotteryAwardPool lotteryAwardPool = lotteryAwardPoolMap.get(probabilityType);

        // 计算抽奖结果，拿到奖项ID
        String awardId = lotteryAwardPool.doDraw(activityId);

        if (Constants.NULL.equals(awardId)) {
            return lotteryAward;
        }

        // 扣减库存
        boolean success = stockStorage.deductStock(activityId, awardId);

        if (!success) {
            // 扣减库存失败,表示未中奖
            return null;
        }

        List<LotteryAwardDto> awardDtos = lotteryActivityRich.getLotteryAwardlDtoList();
        LotteryAwardDto lotteryAwardDto = awardDtos.stream().filter((e) -> e.getAwardId().equals(awardId)).findFirst().get();

        lotteryAward.setActivityId(req.getActivityId());
        lotteryAward.setUserId(req.getUserId());
        lotteryAward.setAwardId(awardId);
        lotteryAward.setActivityName(lotteryActivity.getActivityName());
        lotteryAward.setAwardName(lotteryAwardDto.getAwardName());

        try {
            lotteryOrderRpcService.createOrder(lotteryAward, snowflake.nextIdStr());
        } catch (Exception e) {
            // 如果创建订单失败，需要回滚库存,表示 未中奖
            stockStorage.rollBack(activityId, awardId);
            return null;

        }
        // 表示用户已经抽到奖品了
        log.info("【中奖了】用户：{} ,LotteryId:{}，奖品：{}", req.getUserId(), activityId, awardId);
        return lotteryAward;
    }
}




