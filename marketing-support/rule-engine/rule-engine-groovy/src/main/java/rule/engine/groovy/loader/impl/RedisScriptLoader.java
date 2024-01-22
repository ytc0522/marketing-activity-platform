package rule.engine.groovy.loader.impl;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.lang.NonNull;
import org.springframework.util.CollectionUtils;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;
import rule.engine.groovy.compiler.DynamicCodeCompiler;
import rule.engine.groovy.config.properties.GroovyRedisLoaderProperties;
import rule.engine.groovy.dto.ScriptEntry;
import rule.engine.groovy.dto.ScriptQuery;
import rule.engine.groovy.loader.ScriptLoader;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;


public class RedisScriptLoader implements ScriptLoader {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final RedisTemplate<String, String> redisTemplate;

    private final DynamicCodeCompiler dynamicCodeCompiler;

    private final GroovyRedisLoaderProperties groovyRedisLoaderProperties;

    public RedisScriptLoader(RedisTemplate<String, String> redisTemplate,
                             DynamicCodeCompiler dynamicCodeCompiler,
                             GroovyRedisLoaderProperties groovyRedisLoaderProperties) {
        this.redisTemplate = redisTemplate;
        this.dynamicCodeCompiler = dynamicCodeCompiler;
        this.groovyRedisLoaderProperties = groovyRedisLoaderProperties;
    }

    @Override
    public ScriptEntry load(@NonNull ScriptQuery query) throws Exception {
        // 从Redis中根据key查找脚本
        String script = (String) redisTemplate.opsForHash()
                .get(groovyRedisLoaderProperties.getNamespace(), query.getUniqueKey());
        if (!StringUtils.hasText(script)) {
            return null;
        }
        // 获取脚本指纹
        String fingerprint = DigestUtils.md5DigestAsHex(script.getBytes());
        // 创建脚本对象
        ScriptEntry scriptEntry = new ScriptEntry(query.getUniqueKey(), script, fingerprint, System.currentTimeMillis());
        // 动态加载脚本为Class
        Class<?> aClass = dynamicCodeCompiler.compile(scriptEntry);
        scriptEntry.setClazz(aClass);
        return scriptEntry;
    }

    @Override
    public List<ScriptEntry> load() {
        List<ScriptEntry> resultList = new ArrayList<>();
        String key = groovyRedisLoaderProperties.getNamespace();

        // 获取到所有脚本的key
        Set<Object> hashKeys = redisTemplate.opsForHash().keys(key);
        // 没有脚本
        if (CollectionUtils.isEmpty(hashKeys)) {
            logger.error("can not found hashKeys by key [{}].", key);
            return resultList;
        }

        // 获取所有脚本
        for (Object hashKey : hashKeys) {
            // groovy脚本内容
            String script = (String) redisTemplate.opsForHash().get(key, hashKey);
            if (!StringUtils.hasText(script)) {
                logger.error("note can not found script content by key [{}] and hashKey [{}]", key, hashKey);
                continue;
            }
            // 获取脚本指纹
            String fingerprint = DigestUtils.md5DigestAsHex(script.getBytes());
            // 创建脚本对象
            ScriptEntry scriptEntry = new ScriptEntry(hashKey.toString(), script, fingerprint, System.currentTimeMillis());
            resultList.add(scriptEntry);
        }

        return resultList;
    }
}
