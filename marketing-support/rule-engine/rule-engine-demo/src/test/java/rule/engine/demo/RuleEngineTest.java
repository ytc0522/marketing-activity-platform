package rule.engine.demo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.ResourceUtils;
import org.testng.reporters.Files;
import rule.engine.demo.controller.RuleEngineTestController;
import rule.engine.groovy.dto.EngineExecutorResult;
import rule.engine.groovy.dto.ExecuteParams;
import rule.engine.groovy.dto.ScriptQuery;
import rule.engine.groovy.executor.EngineExecutor;
import rule.engine.groovy.helper.ApplicationContextHelper;
import rule.engine.groovy.loader.helper.ManualRegisterScriptHelper;

import javax.annotation.Resource;
import java.io.File;


@RunWith(SpringRunner.class)
@SpringBootTest
public class RuleEngineTest {

    @Resource
    private ManualRegisterScriptHelper manualRegisterScriptHelper;

    @Resource
    private EngineExecutor executor;

    @Resource
    private ApplicationContextHelper contextHelper;

    String key = "activity:1";

    @Test
    public void testSave() throws Exception {
        String content = "package rule.engine.demo.groovy\n" +
                "\n" +
                "class PersonCheck {\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "}";
        boolean success = manualRegisterScriptHelper.registerScript(key, content, true);

        System.out.println("success = " + success);

    }


    @Test
    public void testQuery() {
        ExecuteParams executeParams = new ExecuteParams();
        executeParams.put("age", 20);
        executeParams.put("isNew", true);

        EngineExecutorResult execute = executor.execute("sayHello", new ScriptQuery(key), executeParams);
        System.out.println("execute = " + execute);

    }

    @Test
    public void testGroovy() throws Exception {
        // 加载 脚本内容
        File file = ResourceUtils.getFile("classpath:script/UserCheck.groovy");

        String content = Files.readFile(file);

        // 先注册
        boolean success = manualRegisterScriptHelper.registerScript(key, content, true);
        System.out.println("success = " + success);

        ExecuteParams executeParams = new ExecuteParams();

        EngineExecutorResult execute = executor.execute("check", new ScriptQuery(key), executeParams);

        System.out.println("execute = " + execute);
    }

    @Test
    public void testBean() {
        RuleEngineTestController bean = ApplicationContextHelper.getBean(RuleEngineTestController.class);
        System.out.println("bean = " + bean);
    }


}
