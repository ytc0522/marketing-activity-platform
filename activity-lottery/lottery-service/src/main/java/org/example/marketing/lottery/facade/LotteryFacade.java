package org.example.marketing.lottery.facade;

import com.alibaba.dubbo.config.annotation.Service;
import lombok.extern.slf4j.Slf4j;
import org.example.marketing.lottery.domain.pool.ILotteryAwardPool;
import org.example.marketing.lottery.rpc.ILotteryDraw;
import org.example.marketing.lottery.rpc.constants.Constants;
import org.example.marketing.lottery.rpc.dto.LotteryDetailDto;
import org.example.marketing.lottery.rpc.dto.LotteryDto;
import org.example.marketing.lottery.rpc.dto.LotteryRich;
import org.example.marketing.lottery.rpc.dto.WinAward;
import org.example.marketing.lottery.rpc.req.DrawReq;
import org.example.marketing.lottery.service.LotteryService;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class LotteryFacade implements ILotteryDraw {

    @Resource
    private LotteryService lotteryService;

    @Resource
    private Map<Integer, ILotteryAwardPool> lotteryAwardPoolMap;


    @Override
    public boolean prepare(Long lotteryId) {
        LotteryRich lotteryRich = lotteryService.refreshCache(lotteryId);
        ILotteryAwardPool lotteryAwardPool = lotteryAwardPoolMap.get(lotteryRich.getLottery().getChanceType());
        lotteryAwardPool.refreshPool(lotteryRich);
        return true;
    }

    @Override
    public WinAward draw(DrawReq req) {

        WinAward award = new WinAward();

        // 从缓存中获取 抽奖相关信息
        LotteryRich lotteryRich = lotteryService.getFromCache(req.getLotteryId());

        LotteryDto lottery = lotteryRich.getLottery();

        Long lotteryId = lottery.getId();

        Integer chanceType = lottery.getChanceType();
        if (chanceType == null) chanceType = 0;

        ILotteryAwardPool lotteryAwardPool = lotteryAwardPoolMap.get(chanceType);

        // 计算抽奖结果，拿到奖项ID  这个时候缓存的库存已经扣减了
        String awardId = lotteryAwardPool.doDraw(lotteryId);

        if (Constants.NULL.equals(awardId)) {
            return award;
        }

        // 表示用户已经抽到奖品了
        log.info("【中奖了】用户：{} 参加抽奖,LotteryId:{}， 抽到奖品：{}", req.getUserId(), lotteryId, awardId);


        List<LotteryDetailDto> details = lotteryRich.getLotteryDetailDtoList();
        LotteryDetailDto detail = details.stream().filter((e) -> e.getAwardId().equals(awardId)).findFirst().get();

        WinAward winAward = award;
        winAward.setAwardId(awardId);
        winAward.setAwardName(detail.getAwardName());
        winAward.setAwardType(detail.getAwardType());
        winAward.setAwardContent(detail.getAwardContent());
        winAward.setGrantType(lottery.getGrantType());

        return winAward;
    }
}
