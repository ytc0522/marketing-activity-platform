package org.example.marketing.lottery.facade;

import com.alibaba.dubbo.config.annotation.Service;
import lombok.extern.slf4j.Slf4j;
import org.example.marketing.common.ActionResult;
import org.example.marketing.lottery.rpc.ILotteryDraw;
import org.example.marketing.lottery.rpc.dto.AwardInfo;
import org.example.marketing.lottery.rpc.req.DrawReq;
import org.example.marketing.lottery.service.LotteryDetailService;
import org.example.marketing.lottery.service.LotteryService;

import javax.annotation.Resource;

@Slf4j
@Service
public class LotteryFacade implements ILotteryDraw {

    @Resource
    private LotteryDetailService lotteryDetailService;

    @Resource
    private LotteryService lotteryService;


    @Override
    public ActionResult<AwardInfo> draw(DrawReq req) {

        // 初始化奖项数据到缓存中

        // 计算抽奖结果，拿到奖项ID

        // 更新库存

        // 抽奖记录写入数据库

        return ActionResult.success("finish");
    }
}
