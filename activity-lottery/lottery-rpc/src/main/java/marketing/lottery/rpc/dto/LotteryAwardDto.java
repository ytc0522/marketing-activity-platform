package marketing.lottery.rpc.dto;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class LotteryAwardDto implements Serializable {

    /**
     * 自增ID
     */
    private Long id;


    private String lotteryId;

    /**
     * 奖品ID
     */
    private String awardId;

    /**
     * 奖品描述
     */
    private String awardName;

    /**
     * 奖品库存
     */
    private Integer initCount;

    /**
     * 奖品剩余库存
     */
    private Integer availableCount;

    /**
     * 中奖概率
     */
    private BigDecimal probability;

    /**
     * 版本
     */
    private int version;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改时间
     */
    private Date updateTime;

    private static final long serialVersionUID = 1L;


}
