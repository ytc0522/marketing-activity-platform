package org.example.marketing.lottery.facade;

import com.alibaba.dubbo.config.annotation.Service;
import lombok.extern.slf4j.Slf4j;
import org.example.marketing.common.ActionResult;
import org.example.marketing.lottery.rpc.ILotteryDraw;
import org.example.marketing.lottery.rpc.dto.AwardInfo;
import org.example.marketing.lottery.rpc.req.DrawReq;
import org.example.marketing.lottery.domain.award.AwardService;

import javax.annotation.Resource;

@Slf4j
@Service
public class LotteryFacade implements ILotteryDraw {

    @Resource
    private AwardService awardService;


    @Override
    public ActionResult<AwardInfo> draw(DrawReq req) {

        // 写库，扣减库存
        log.info("===== lottery facade ======");


        return ActionResult.success("finish");
    }
}
