package rule.engine.demo.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class ScriptResult implements Serializable {

    private static final long serialVersionUID = 584589360204188718L;

    private boolean ok;

    private String reason;


    public static ScriptResult ok() {
        ScriptResult scriptResult = new ScriptResult();
        scriptResult.setOk(true);
        return scriptResult;
    }

}
