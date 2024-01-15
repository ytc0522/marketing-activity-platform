package org.example.marketing.activity.order.repository.sharding;

import org.apache.shardingsphere.api.sharding.standard.PreciseShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue;
import org.springframework.util.StringUtils;

import java.util.Collection;

/**
 * 主流的切分策略有3种：Range 范围、hash切分、映射表。
 */
public class PreciseHashShardingConfig implements PreciseShardingAlgorithm<String> {


    @Override
    public String doSharding(Collection<String> availableTargetNames, PreciseShardingValue<String> shardingValue) {

        String value = shardingValue.getValue();        // 具体的分片键的值
        if (StringUtils.isEmpty(value)) {
            throw new UnsupportedOperationException("分片键值不能为空");
        }
        // 确定哪一张表
        int index = hash(value) & (availableTargetNames.size() - 1);
        String[] array = availableTargetNames.toArray(new String[0]);
        return array[index];
    }

    static int hash(Object key) {
        int h;
        return (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);
    }


}
