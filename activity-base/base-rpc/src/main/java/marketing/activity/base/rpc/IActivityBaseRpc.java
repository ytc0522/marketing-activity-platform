package marketing.activity.base.rpc;

import marketing.activity.base.rpc.model.RequirementsReqDto;
import marketing.activity.base.rpc.model.RequirementsResultDto;

public interface IActivityBaseRpc {

    /**
     * 校验用户是否具备参加活动资格
     *
     * @param req
     * @return
     */
    public RequirementsResultDto check(RequirementsReqDto req);


    public void writeLog();


}
