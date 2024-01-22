package rule.engine.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rule.engine.groovy.dto.EngineExecutorResult;
import rule.engine.groovy.dto.ExecuteParams;
import rule.engine.groovy.dto.ScriptQuery;
import rule.engine.groovy.executor.EngineExecutor;
import rule.engine.groovy.loader.helper.ManualRegisterScriptHelper;

import javax.annotation.Resource;

@RestController("ruleEngineTestController")
@RequestMapping("/ruleEngine")
public class RuleEngineTestController {

    @Resource
    private ManualRegisterScriptHelper manualRegisterScriptHelper;

    @Resource
    private EngineExecutor executor;

    String key = "activity:1";

    @GetMapping("/test")
    public String test() throws Exception {

        String content = "package rule.engine.demo.groovy\n" +
                "\n" +
                "import rule.engine.groovy.dto.ExecuteParams\n" +
                "\n" +
                "class PersonCheck extends Script{\n" +
                "\n" +
                "\n" +
                "    boolean sayHello(ExecuteParams params) {\n" +
                "        def age = params.get(\"age\")\n" +
                "        if (age > 10 ){\n" +
                "            return true\n" +
                "        }\n" +
                "        return false;\n" +
                "    }\n" +
                "\n" +
                "    @Override\n" +
                "    Object run() {\n" +
                "        return null\n" +
                "    }\n" +
                "}\n";
        boolean success = manualRegisterScriptHelper.registerScript(key, content, true);

        System.out.println("success = " + success);

        ExecuteParams executeParams = new ExecuteParams();
        executeParams.put("age", 20);
        executeParams.put("isNew", true);

        EngineExecutorResult execute = executor.execute("sayHello", new ScriptQuery(key), executeParams);
        System.out.println("execute = " + execute);
        Object context = execute.getContext();
        System.out.println("context = " + context);

        return "S";
    }


}
