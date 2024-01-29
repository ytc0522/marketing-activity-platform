package marketing.activity.lottery.rpc.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class LotteryActivityRich implements Serializable {

    private static final long serialVersionUID = 1L;

    private LotteryActivityDto lotteryActivity;

    private List<LotteryAwardDto> lotteryAwardlDtoList;

}
