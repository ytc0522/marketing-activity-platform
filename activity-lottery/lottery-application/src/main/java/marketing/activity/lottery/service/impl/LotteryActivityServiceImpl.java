package marketing.activity.lottery.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import marketing.activity.infrastructure.util.RedisUtil;
import marketing.activity.lottery.domain.pool.ILotteryAwardPool;
import marketing.activity.lottery.infrastructure.repository.entity.LotteryActivity;
import marketing.activity.lottery.infrastructure.repository.entity.LotteryAward;
import marketing.activity.lottery.infrastructure.repository.mapper.LotteryActivityMapper;
import marketing.activity.lottery.order.ILotteryOrderRpcService;
import marketing.activity.lottery.service.LotteryActivityService;
import marketing.activity.lottery.service.LotteryAwardService;
import marketing.lottery.rpc.dto.LotteryActivityDto;
import marketing.lottery.rpc.dto.LotteryActivityRich;
import marketing.lottery.rpc.dto.LotteryAwardDto;
import org.apache.dubbo.config.annotation.DubboReference;
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
        implements LotteryActivityService {


    @Resource
    private LotteryAwardService lotteryAwardService;

    @Resource
    private RedisUtil redisUtil;

    private static String LOTTERY_KEY = "LOTTERY_KEY:";

    @Resource
    private Map<Integer, ILotteryAwardPool> lotteryAwardPoolMap;

    @DubboReference
    private ILotteryOrderRpcService lotteryOrderRpcService;


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

        LotteryActivity lotteryActivity = this.lambdaQuery()
                .eq(LotteryActivity::getActivityId, lotteryActivityId)
                .last("limit 1")
                .one();
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


}




