package ${groupId}.mvc.bean.interact.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.util.Assert;

import java.util.HashMap;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class ErrorResult {


    public ErrorResult(Integer error_no) {
        this.error_no = error_no;
    }


    public ErrorResult(Integer error_no, String error_msg) {
        this.error_no = error_no;
        this.error_msg = error_msg;
    }

    public ErrorResult(String error_msg) {
        this.error_msg = error_msg;
    }

    public ErrorResult(Integer error_no, Object errors) {
        this.error_no = error_no;
        this.errors = errors;
    }

    public static ${groupId}.mvc.bean.interact.response.ErrorResult error(Integer error_no, String error_msg) {
        return new ${groupId}.mvc.bean.interact.response.ErrorResult(error_no, error_msg);
    }

    public static ${groupId}.mvc.bean.interact.response.ErrorResult error(String error) {
        return new ${groupId}.mvc.bean.interact.response.ErrorResult(error);
    }

    public static ${groupId}.mvc.bean.interact.response.ErrorResult error(Integer error_no, Object error) {
        return new ${groupId}.mvc.bean.interact.response.ErrorResult(error_no, error);
    }


    public static ${groupId}.mvc.bean.interact.response.ErrorResult errors(Integer error_no, String... errors) {
        Assert.isTrue(errors.length % 2 == 0, "传入的参数个数必须为偶数");
        Map<String, String> map = new HashMap<>();
        for (int i = 0; i < errors.length / 2; ++i) {
            map.put(errors[0], errors[1]);
        }
        return new ${groupId}.mvc.bean.interact.response.ErrorResult(error_no, map);
    }

    private Integer error_no;
    private String error_msg;
    private Object errors;
    private boolean success = false;
}
