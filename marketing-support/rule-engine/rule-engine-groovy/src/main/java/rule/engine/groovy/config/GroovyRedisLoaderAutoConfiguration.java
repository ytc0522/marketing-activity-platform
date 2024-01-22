package rule.engine.groovy.config;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import rule.engine.groovy.compiler.DynamicCodeCompiler;
import rule.engine.groovy.config.properties.GroovyRedisLoaderProperties;
import rule.engine.groovy.loader.ScriptLoader;
import rule.engine.groovy.loader.helper.ManualRegisterScriptHelper;
import rule.engine.groovy.loader.impl.RedisScriptLoader;
import rule.engine.groovy.registry.ScriptRegistry;


@Configuration
@EnableConfigurationProperties(value = {GroovyRedisLoaderProperties.class})
public class GroovyRedisLoaderAutoConfiguration {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * <p>
     * 需要依赖于RedisTemplate，所以项目里必须要配置redis
     * </p>
     */
    @Bean
    public ScriptLoader redisScriptLoader(RedisTemplate<String, String> redisTemplate,
                                          DynamicCodeCompiler dynamicCodeCompiler,
                                          GroovyRedisLoaderProperties groovyRedisLoaderProperties) {
        logger.info("loading ScriptLoader type is [{}]", RedisScriptLoader.class);
        return new RedisScriptLoader(redisTemplate, dynamicCodeCompiler, groovyRedisLoaderProperties);
    }

    /**
     * 注入手动注册脚本助手
     */
    @Bean
    public ManualRegisterScriptHelper registerScriptHelper(ScriptRegistry scriptRegistry,
                                                           ScriptLoader scriptLoader,
                                                           RedisTemplate<String, String> redisTemplate,
                                                           GroovyRedisLoaderProperties groovyRedisLoaderProperties) {
        return new ManualRegisterScriptHelper(scriptRegistry, scriptLoader, redisTemplate, groovyRedisLoaderProperties);
    }

}
