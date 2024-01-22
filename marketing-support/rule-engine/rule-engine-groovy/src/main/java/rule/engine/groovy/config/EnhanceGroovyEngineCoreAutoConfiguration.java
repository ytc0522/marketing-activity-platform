package rule.engine.groovy.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import rule.engine.groovy.alarm.HotLoadingGroovyScriptAlarm;
import rule.engine.groovy.config.properties.GroovyEngineProperties;
import rule.engine.groovy.executor.AutoRefreshScriptExecutor;
import rule.engine.groovy.helper.ApplicationContextHelper;
import rule.engine.groovy.helper.RefreshScriptHelper;

/**
 * 自动配置类
 *
 * @author wenpan 2022/09/18 14:25
 */
@Slf4j
@Configuration
@EnableConfigurationProperties(value = {GroovyEngineProperties.class})
public class EnhanceGroovyEngineCoreAutoConfiguration {

    /**
     * 自动刷新脚本executor
     */
    @Bean
    public AutoRefreshScriptExecutor autoRefreshScriptExecutor(GroovyEngineProperties groovyEngineProperties,
                                                               RefreshScriptHelper refreshScriptHelper) {

        return new AutoRefreshScriptExecutor(groovyEngineProperties, refreshScriptHelper);
    }

    @Bean
    @ConditionalOnMissingBean(HotLoadingGroovyScriptAlarm.class)
    public HotLoadingGroovyScriptAlarm hotLoadingGroovyScriptAlarm() {
        // 默认打印告警信息到日志里
        return (scriptEntries, ex) -> log.error("scriptEntry load failed, scriptEntries info is : {}", scriptEntries, ex);
    }

    /**
     * spring容器助手
     */
    @Bean
    public ApplicationContextHelper applicationContextHelper() {

        return new ApplicationContextHelper();
    }

    /**
     * <p>
     * 导入CoreAutoConfiguration（springboot中默认的加载顺序是：先根据spring.factories文件读取到
     * EnhanceGroovyEngineCoreAutoConfiguration类，然后处理里面的@Import 注解，所以ImportCoreAutoConfiguration里的bean
     * 会优先于EnhanceGroovyEngineCoreAutoConfiguration所有的bean的注入）
     * </p>
     */
    @Import(value = {CoreAutoConfiguration.class})
    static class ImportCoreAutoConfiguration {

    }

}
