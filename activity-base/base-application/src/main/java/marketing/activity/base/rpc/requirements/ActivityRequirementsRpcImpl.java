package marketing.activity.base.rpc.requirements;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.dubbo.config.annotation.Service;
import marketing.activity.base.domain.requirements.IActivityRequirements;
import marketing.activity.base.domain.requirements.model.RequirementsReq;
import marketing.activity.base.domain.requirements.model.RequirementsResult;
import marketing.activity.base.rpc.IActivityBaseRpc;
import marketing.activity.base.rpc.model.RequirementsReqDto;
import marketing.activity.base.rpc.model.RequirementsResultDto;

import javax.annotation.Resource;

@Service
public class ActivityRequirementsRpcImpl implements IActivityBaseRpc {


    @Resource
    private IActivityRequirements requirements;


    /**
     * @param req
     * @return
     */
    @Override
    public RequirementsResultDto check(RequirementsReqDto req) {
        RequirementsReq requirementsReq = BeanUtil.copyProperties(req, RequirementsReq.class);
        RequirementsResult result = requirements.check(requirementsReq);

        RequirementsResultDto dto = BeanUtil.copyProperties(result, RequirementsResultDto.class);
        return dto;
    }
}
