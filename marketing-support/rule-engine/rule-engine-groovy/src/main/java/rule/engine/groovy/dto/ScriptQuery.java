package rule.engine.groovy.dto;

import com.google.common.base.Preconditions;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

@Data
public class ScriptQuery {

    /**
     * 唯一键
     */
    private String uniqueKey;

    public ScriptQuery(String uniqueKey) {
        Preconditions.checkArgument(StringUtils.isNotBlank(uniqueKey), "uniqueKey can not be null.");
        this.uniqueKey = uniqueKey;
    }

}