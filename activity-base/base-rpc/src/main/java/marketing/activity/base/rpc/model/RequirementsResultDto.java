package marketing.activity.base.rpc.model;

import lombok.Data;

@Data
public class RequirementsResultDto {

    /**
     * 是否具备资格参加活动
     */
    private boolean able;

    /**
     * 不能参加的原因
     */
    private String reason;


    public static RequirementsResultDto ok() {
        RequirementsResultDto requirementsResult = new RequirementsResultDto();
        requirementsResult.setAble(true);
        return requirementsResult;
    }

    public boolean isOk() {
        return this.able;
    }

    public static RequirementsResultDto notOK() {
        RequirementsResultDto requirementsResult = new RequirementsResultDto();
        requirementsResult.setAble(false);
        return requirementsResult;
    }


}
