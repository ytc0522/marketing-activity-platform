package marketing.activity.backend.enums;

public enum ActivityType {

    LOTTERY(0),
    SEC_KILL(1),
    PIN_TUAN(2);


    Integer code;

    ActivityType(Integer code) {
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }

    public static ActivityType getByCode(Integer code) {
        for (ActivityType type : ActivityType.values()) {
            if (type.code.equals(code)) {
                return type;
            }
        }
        return null;
    }
}
