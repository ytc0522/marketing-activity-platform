package marketing.activity.infrastructure.event.body;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class UserActivityLogEventBody {

    private String userId;

    private String userName;

    private String clientIp;

    private Long activityId;

    private String activityName;

    // 操作类型 比如 浏览、下单、
    private String operateType;

    private Date date;

    private Integer status;

}
