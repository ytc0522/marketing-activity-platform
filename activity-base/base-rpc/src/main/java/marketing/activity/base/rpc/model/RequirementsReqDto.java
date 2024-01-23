package marketing.activity.base.rpc.model;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class RequirementsReqDto implements Serializable {

    private static final long serialVersionUID = -5087216323923421365L;
    private String userId;

    private Long activityId;

}
