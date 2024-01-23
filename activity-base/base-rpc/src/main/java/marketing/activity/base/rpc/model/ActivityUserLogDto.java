package marketing.activity.base.rpc.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class ActivityUserLogDto implements Serializable {

    /**
     *
     */
    private Long id;

    /**
     *
     */
    private String userId;

    /**
     *
     */
    private Long activityId;

    /**
     * 活动类型
     */
    private String activityType;


    /**
     * 用户操作类型：
     * 浏览、参加、下单、手动取消订单、自动取消订单
     */
    private String operateType;

    /**
     * 0:未完成 1:已完成
     */
    private Integer state;

    /**
     *
     */
    private Date date;

    private static final long serialVersionUID = 1L;


}
