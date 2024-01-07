package org.example.marketing.lottery.facade;

import com.alibaba.dubbo.config.annotation.Service;
import lombok.extern.slf4j.Slf4j;
import org.example.marketing.common.ActionResult;
import org.example.marketing.lottery.rpc.ILotteryDraw;
import org.example.marketing.lottery.rpc.dto.AwardInfo;
import org.example.marketing.lottery.rpc.req.DrawReq;
import org.example.marketing.lottery.domain.award.AwardService;
import org.example.marketing.lottery.service.LotteryDetailService;

import javax.annotation.Resource;

@Slf4j
@Service
public class LotteryFacade implements ILotteryDraw {

    @Resource
    private AwardService awardService;

    @Resource
    private LotteryDetailService lotteryDetailService;


    @Override
    public ActionResult<AwardInfo> draw(DrawReq req) {
        Long lotteryId = req.getLotteryId();

        //



        return ActionResult.success("finish");
    }
}
