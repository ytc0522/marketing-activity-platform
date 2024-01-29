package marketing.activity.lottery.rpc.req;

import lombok.Data;

/**
 * * @Author: jack
 * * @Date    2024/1/28 21:08
 * * @Description 相信坚持的力量！
 **/
@Data
public class ActivityQueryReq extends PageReq {

    private Integer activityType;

    private String activityName;

}
