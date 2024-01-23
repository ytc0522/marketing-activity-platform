package marketing.activity.base.domain.requirements.model;

import lombok.Data;

@Data
public class RequirementsResult {

    /**
     * 是否具备资格参加活动
     */
    private boolean able;

    /**
     * 不能参加的原因
     */
    private String reason;


    public static RequirementsResult ok() {
        RequirementsResult requirementsResult = new RequirementsResult();
        requirementsResult.setAble(true);
        return requirementsResult;
    }

    public boolean isOk() {
        return this.able;
    }

    public static RequirementsResult notOK() {
        RequirementsResult requirementsResult = new RequirementsResult();
        requirementsResult.setAble(false);
        return requirementsResult;
    }


}
