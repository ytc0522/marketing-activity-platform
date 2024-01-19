package marketing.activity.infrastructure.util;

import cn.hutool.core.lang.Snowflake;

public class SnowflakeUtil {


    private static Snowflake snowflake;

    static {
        snowflake = new Snowflake();
    }

    public static String nextIdStr() {
        return snowflake.nextIdStr();
    }

    public static Long nextId() {
        return snowflake.nextId();
    }

}
