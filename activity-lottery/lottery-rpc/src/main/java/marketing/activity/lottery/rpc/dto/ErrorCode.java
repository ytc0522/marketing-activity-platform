package marketing.activity.lottery.rpc.dto;

/**
 * @author : CM
 * @date : 2019/3/4
 */
public enum ErrorCode implements IErrorCode {
    SUCCESS(0, "操作成功"),
    FAILURE(1, "操作失败"),
    PARAMS_INVALID(2, "参数无效,操作失败"),
    Empty(3,"无符合条件的记录"),
    UPDATE_ERROR(4, "更新异常"),
    UN_AUTH(401, "未登录"),
    IN_BLACKLIST(402, "您暂无访问权限"),
    NO_AUTHORITY(403, "没有权限，请联系管理员授权"),
    UN_KNOWN_EXCEPTION(500, "前方活动太火爆啦,请稍后再试～～");

    private int code;
    private String msg;

    ErrorCode(int code, String msg) {
        this.code = BASE_CODE + code;
        this.msg = msg;
    }

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public String getMsg() {
        return msg;
    }
}
