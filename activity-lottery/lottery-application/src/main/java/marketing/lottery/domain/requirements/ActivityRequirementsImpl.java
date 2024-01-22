package marketing.lottery.domain.requirements;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import marketing.lottery.domain.requirements.model.RequirementsReq;
import marketing.lottery.domain.requirements.model.RequirementsResult;
import marketing.lottery.repository.entity.ActivityRequirements;
import marketing.lottery.repository.mapper.ActivityRequirementsMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import rule.engine.groovy.constants.ExecutionStatus;
import rule.engine.groovy.dto.EngineExecutorResult;
import rule.engine.groovy.dto.ExecuteParams;
import rule.engine.groovy.dto.ScriptQuery;
import rule.engine.groovy.executor.EngineExecutor;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

@Service
public class ActivityRequirementsImpl implements IActivityRequirements {


    @Resource
    private ActivityRequirementsMapper mapper;

    @Resource
    private EngineExecutor executor;

    /**
     * @param req
     * @return
     */
    @Override
    public RequirementsResult check(RequirementsReq req) {
        // 查询用户数据，包括用户的基本信息、消费数据、行为信息等）

        Long activityId = req.getActivityId();
        // 查询活动的条件
        LambdaQueryWrapper<ActivityRequirements> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ActivityRequirements::getActivityId, activityId);
        List<ActivityRequirements> list = mapper.selectList(wrapper);

        if (CollectionUtils.isEmpty(list)) {
            return RequirementsResult.ok();
        }


        ExecuteParams executeParams = new ExecuteParams();
        executeParams.put("userContext", new HashMap<>());   // 模拟放入用户数据
        // 调用规则引擎
        for (ActivityRequirements requirements : list) {
            EngineExecutorResult result = executor.execute(new ScriptQuery(requirements.getRuleKey()), executeParams);
            // 如果结果不正确，停止
            if (result.getExecutionStatus() != ExecutionStatus.SUCCESS || !((boolean) result.getContext())) {
                return RequirementsResult.notOK();
            }
        }

        return RequirementsResult.ok();
    }
}
