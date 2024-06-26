package org.example.marketing.lottery.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.example.marketing.common.ActionResult;
import org.example.marketing.lottery.repository.entity.LotteryDetail;
import org.example.marketing.lottery.rpc.req.DrawReq;

/**
* @author jack
* @description 针对表【lottery(策略明细)】的数据库操作Service
* @createDate 2024-01-07 19:47:12
*/
public interface LotteryDetailService extends IService<LotteryDetail> {


    ActionResult draw(DrawReq req);

}
