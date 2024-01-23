package marketing.activity.base.domain.requirements;


import marketing.activity.base.domain.requirements.model.RequirementsReq;
import marketing.activity.base.domain.requirements.model.RequirementsResult;

public interface IActivityRequirements {


    RequirementsResult check(RequirementsReq req);


}
