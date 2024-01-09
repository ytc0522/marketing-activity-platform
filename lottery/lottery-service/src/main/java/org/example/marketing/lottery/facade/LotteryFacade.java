package org.example.marketing.lottery.facade;

import com.alibaba.dubbo.config.annotation.Service;
import lombok.extern.slf4j.Slf4j;
import org.example.marketing.common.ActionResult;
import org.example.marketing.lottery.domain.pool.FixChanceAwardPool;
import org.example.marketing.lottery.repository.util.RedisUtil;
import org.example.marketing.lottery.rpc.ILotteryDraw;
import org.example.marketing.lottery.rpc.constants.Constants;
import org.example.marketing.lottery.rpc.dto.AwardInfo;
import org.example.marketing.lottery.rpc.dto.LotteryDetailDto;
import org.example.marketing.lottery.rpc.dto.LotteryRich;
import org.example.marketing.lottery.rpc.req.DrawReq;
import org.example.marketing.lottery.service.LotteryDetailService;
import org.example.marketing.lottery.service.LotteryService;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@Service
public class LotteryFacade implements ILotteryDraw {

    @Resource
    private LotteryService lotteryService;

    @Resource
    private FixChanceAwardPool chanceAwardPool;


    @Override
    public ActionResult prepare(Long lotteryId) {
        LotteryRich lotteryRich = lotteryService.refreshCache(lotteryId);
        chanceAwardPool.refreshPool(lotteryRich);
        return ActionResult.success();
    }

    @Override
    public ActionResult<AwardInfo> draw(DrawReq req) {

        // 从缓存中获取 抽奖相关信息
        LotteryRich lotteryRich = lotteryService.getFromCache(req.getLotteryId());

        Long lotteryId = lotteryRich.getLottery().getId();

        // 计算抽奖结果，拿到奖项ID  这个时候缓存的库存已经扣减了
        String awardId = chanceAwardPool.doDraw(lotteryId);

        if (Constants.NULL.equals(awardId)) {
            return ActionResult.success();
        }

        // 表示用户已经抽到奖品了
        log.info("用户：{} 参加抽奖,LotteryId:{}， 抽到奖品：{}", req.getUserId(), lotteryId, awardId);

        // 抽奖记录写入数据库
        // 写入 record表
        // todo

        return ActionResult.success(awardId);
    }
}
