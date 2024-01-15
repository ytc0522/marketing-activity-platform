package org.example.marketing.lottery.rpc.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class LotteryRich implements Serializable {

    private static final long serialVersionUID = 1L;

    private LotteryDto lottery;

    private List<LotteryDetailDto> lotteryDetailDtoList;

}
