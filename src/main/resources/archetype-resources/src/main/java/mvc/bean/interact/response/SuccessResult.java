package ${groupId}.mvc.bean.interact.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class SuccessResult {

    public SuccessResult() {
    }

    public SuccessResult(Object data) {
        this.data = data;
    }

    public static ${groupId}.mvc.bean.interact.response.SuccessResult success(Object data) {
        return new ${groupId}.mvc.bean.interact.response.SuccessResult(data);
    }

    public SuccessResult(Integer success_code, Object data) {
        this.success_code = success_code;
        this.data = data;
    }

    public static ${groupId}.mvc.bean.interact.response.SuccessResult success(Integer success_code, Object data) {
        return new ${groupId}.mvc.bean.interact.response.SuccessResult(success_code, data);
    }

    public static ${groupId}.mvc.bean.interact.response.SuccessResult success() {
        return new ${groupId}.mvc.bean.interact.response.SuccessResult();
    }

    public Object data;

    private boolean success = true;

    private Integer success_code;

}
