package script

import rule.engine.demo.controller.RuleEngineTestController
import rule.engine.demo.model.ScriptResult
import rule.engine.groovy.dto.ExecuteParams
import rule.engine.groovy.helper.ApplicationContextHelper;

class UserCheck extends Script {


    ScriptResult check(ExecuteParams params) {

        /*
         调接口测试
        */
        RuleEngineTestController bean = (RuleEngineTestController) ApplicationContextHelper.getBean(RuleEngineTestController.class)
        println("bean:" + bean)

        return ScriptResult.ok();

    }


    @Override
    Object run() {
        return null
    }

}
