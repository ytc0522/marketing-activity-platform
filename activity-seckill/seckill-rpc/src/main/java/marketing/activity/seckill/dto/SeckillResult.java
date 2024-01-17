package marketing.activity.seckill.dto;

import lombok.Data;

@Data
public class SeckillResult {

    private boolean success;


    public static SeckillResult fail() {
        SeckillResult result = new SeckillResult();
        result.setSuccess(false);
        return result;
    }

}
