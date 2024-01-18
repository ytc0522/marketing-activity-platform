package marketing.activity.seckill.util;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateTime;
import cn.hutool.json.JSONObject;
import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTPayload;
import cn.hutool.jwt.JWTUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

/**
 * jwt工具类
 */
@Slf4j
public class JwtUtil {

    /**
     * 盐值很重要，不能泄漏，且每个项目都应该不一样，可以放到配置文件中
     */
    private static final String KEY = "abc";

    /**
     * 创建token
     *
     * @return
     */
    public static String createToken(Map<String, Object> payloadMap) {
        DateTime now = DateTime.now();
        DateTime expTime = now.offsetNew(DateField.MILLISECOND, 1);
        Map<String, Object> payload = new HashMap<>();
        // 内容
        payload.putAll(payloadMap);
        String token = JWTUtil.createToken(payload, KEY.getBytes());
        return token;
    }

    /**
     * 校验token
     *
     * @param token
     * @return
     */
    public static boolean validate(String token) {

        boolean validate = false;
        try {
            JWT jwt = JWTUtil.parseToken(token).setKey(KEY.getBytes());
            // validate包含了verify
            validate = jwt.validate(0);
        } catch (Exception e) {
            return validate;
        }
        return validate;
    }

    public static JSONObject getJSONObject(String token) {
        JWT jwt = JWTUtil.parseToken(token).setKey(KEY.getBytes());
        JSONObject payloads = jwt.getPayloads();
        payloads.remove(JWTPayload.ISSUED_AT);
        payloads.remove(JWTPayload.EXPIRES_AT);
        payloads.remove(JWTPayload.NOT_BEFORE);
        return payloads;
    }

    public static void main(String[] args) throws InterruptedException {
        HashMap<String, Object> map = new HashMap<>();
        map.put("orderId", "9399300029");
        map.put("userId", "123");

        String t = createToken(map);

        Thread.sleep(2);

        boolean validate = validate("123");

        System.out.println("validate = " + validate);

    }
}

