package rule.engine.groovy.config;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import rule.engine.groovy.compiler.DynamicCodeCompiler;
import rule.engine.groovy.compiler.impl.GroovyCompiler;
import rule.engine.groovy.config.properties.GroovyEngineProperties;
import rule.engine.groovy.dto.ScriptEntry;
import rule.engine.groovy.executor.EngineExecutor;
import rule.engine.groovy.executor.impl.DefaultEngineExecutor;
import rule.engine.groovy.helper.RefreshScriptHelper;
import rule.engine.groovy.loader.ScriptLoader;
import rule.engine.groovy.registry.ScriptRegistry;
import rule.engine.groovy.registry.impl.DefaultScriptRegistry;

import java.util.concurrent.TimeUnit;

/**
 *
 * @author wenpan 2022/09/18 18:11
 */
@Configuration
public class CoreAutoConfiguration {

    /**
     * groovy class编译器
     */
    @Bean
    @ConditionalOnMissingBean(DynamicCodeCompiler.class)
    public DynamicCodeCompiler groovyCompiler() {

        return new GroovyCompiler();
    }

    /**
     * 可由使用方动态替换
     */
    @Bean(name = "enhanceGroovyScriptEngineCache")
    @ConditionalOnMissingBean(name = "enhanceGroovyScriptEngineCache", value = {Cache.class})
    public @NonNull Cache<String, ScriptEntry> enhanceGroovyScriptEngineCache(GroovyEngineProperties groovyEngineProperties) {
        return Caffeine.newBuilder()
                // 设置最后一次写入或访问后经过固定时间过期(默认600分钟)
                .expireAfterWrite(groovyEngineProperties.getCacheExpireAfterWrite(), TimeUnit.MINUTES)
                // 初始的缓存空间大小
                .initialCapacity(groovyEngineProperties.getCacheInitialCapacity())
                // 缓存的最大条数
                .maximumSize(groovyEngineProperties.getCacheMaximumSize())
                .build();
    }

    /**
     * 脚本注册中心，依赖于 ScriptLoader ，ScriptLoader实现类由使用方自由选配
     */
    @Bean
    @ConditionalOnMissingBean(ScriptRegistry.class)
    public ScriptRegistry scriptRegistry(ScriptLoader scriptLoader,
                                         @Qualifier("enhanceGroovyScriptEngineCache") Cache<String, ScriptEntry> cache) {

        return new DefaultScriptRegistry(scriptLoader, cache);
    }

    /**
     * 执行引擎
     */
    @Bean
    @ConditionalOnMissingBean(EngineExecutor.class)
    public EngineExecutor defaultEngineExecutor(ScriptRegistry scriptRegistry) {

        return new DefaultEngineExecutor(scriptRegistry);
    }

    /**
     * 注入刷新groovy脚本助手
     */
    @Bean
    @ConditionalOnMissingBean(RefreshScriptHelper.class)
    public RefreshScriptHelper refreshScriptHelper() {

        return new RefreshScriptHelper();
    }
}
