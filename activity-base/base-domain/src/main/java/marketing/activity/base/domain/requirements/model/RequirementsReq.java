package marketing.activity.base.domain.requirements.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RequirementsReq {

    private String userId;

    private Long activityId;

}
