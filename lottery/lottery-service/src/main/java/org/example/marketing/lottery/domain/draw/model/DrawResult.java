package org.example.marketing.lottery.domain.draw.model;

import lombok.Data;
import org.example.marketing.lottery.common.enums.DrawState;
import org.example.marketing.lottery.domain.draw.model.vo.DrawAwardVO;

@Data
public class DrawResult {

    /**
     * 用户ID
     */
    private String userId;

    /**
     * 策略ID
     */
    private Long strategyId;

    /**
     * 中奖状态：0未中奖、1已中奖、2兜底奖 Constants.DrawState
     */
    private Integer drawState = DrawState.FAIL.getCode();

    /**
     * 中奖奖品信息
     */
    private DrawAwardVO drawAwardInfo;



}
