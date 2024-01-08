package org.example.marketing.lottery.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.example.marketing.common.ActionResult;
import org.example.marketing.lottery.repository.entity.Lottery;
import org.example.marketing.lottery.repository.entity.LotteryDetail;
import org.example.marketing.lottery.repository.mapper.LotteryMapper;
import org.example.marketing.lottery.repository.util.RedisUtil;
import org.example.marketing.lottery.rpc.dto.AwardInfo;
import org.example.marketing.lottery.rpc.dto.LotteryDetailDto;
import org.example.marketing.lottery.rpc.dto.LotteryDto;
import org.example.marketing.lottery.rpc.dto.LotteryRich;
import org.example.marketing.lottery.rpc.req.DrawReq;
import org.example.marketing.lottery.service.LotteryDetailService;
import org.example.marketing.lottery.service.LotteryService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
* @author jack
* @description 针对表【lottery】的数据库操作Service实现
* @createDate 2024-01-07 20:39:51
*/
@Service
public class LotteryServiceImpl extends ServiceImpl<LotteryMapper, Lottery>
    implements LotteryService {


    @Resource
    private LotteryDetailService lotteryDetailService;

    @Resource
    private RedisUtil redisUtil;

    private static String LOTTERY_KEY = "LOTTERY_KEY:";

    @Override
    public LotteryRich getFromCache(Long lotteryId) {
        String lotteryCacheKey = LOTTERY_KEY + lotteryId;

        LotteryRich lotteryRich = (LotteryRich)redisUtil.get(lotteryCacheKey);

        if (lotteryRich != null) {
            return lotteryRich;
        }

        Lottery lottery = this.getById(lotteryId);
        if (lottery == null) {
            return null;
        }
        List<LotteryDetail> detailList = lotteryDetailService.lambdaQuery().eq(LotteryDetail::getLotteryId, lotteryId)
                .list();

        LotteryRich newLotteryRich = new LotteryRich();
        LotteryDto lotteryDto = new LotteryDto();
        BeanUtil.copyProperties(lottery,lotteryDto);

        List<LotteryDetailDto> lotteryDetailDtos = BeanUtil.copyToList(detailList, LotteryDetailDto.class);

        newLotteryRich.setLottery(lotteryDto);
        newLotteryRich.setLotteryDetailDtoList(lotteryDetailDtos);

        redisUtil.set(lotteryCacheKey,newLotteryRich,3600 * 3);
        return newLotteryRich;

    }

    @Override
    public boolean deductStock(Long lotteryId, String awardId) {

        UpdateWrapper<LotteryDetail> updateWrapper = new UpdateWrapper<>();
        updateWrapper.setSql("award_surplus_count = award_surplus_count - 1");
        updateWrapper.eq("lottery_id",lotteryId);
        updateWrapper.eq("award_id",awardId);

        return lotteryDetailService.update(updateWrapper);
    }
}




