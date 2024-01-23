package marketing.activity.base.rpc;

import marketing.activity.base.rpc.model.RequirementsReqDto;
import marketing.activity.base.rpc.model.RequirementsResultDto;

public interface IActivityBaseRpc {

    public RequirementsResultDto check(RequirementsReqDto req);


}
