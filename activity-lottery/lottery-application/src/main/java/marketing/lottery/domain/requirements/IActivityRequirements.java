package marketing.lottery.domain.requirements;

import marketing.lottery.domain.requirements.model.RequirementsReq;
import marketing.lottery.domain.requirements.model.RequirementsResult;

public interface IActivityRequirements {


    RequirementsResult check(RequirementsReq req);


}
