package ${groupId}.mvc.bean.interact.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonView;
import ${groupId}.utils.jsonview.CommJsonView;
import lombok.Data;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonView(CommJsonView.class)
public class Result {
    private boolean success;
    private Integer code;
    public Object data;


    private Result(boolean success, Integer code) {
        this.success = success;
        this.code = code;
    }

    private Result(boolean success, Integer code, Object data) {
        this.code = code;
        this.data = data;
        this.success = success;
    }

    private Result(boolean success, Object data) {
        this.data = data;
        this.success = success;
    }

    public static Result success(Integer code, Object data) {
        return new Result(true, code, data);
    }

    public static Result success(Object data) {
        return new Result(true, data);
    }

    public static Result success(Page page) {
        return new Result(true, new PageResult(page));
    }

    public static ResponseEntity<Result> error(Integer code, Object data) {
        return ResponseEntity.status(HttpStatus.valueOf(code / 100)).body(new Result(false, code, data));
    }

    public static Result error(Object data) {
        return new Result(false, data);
    }


}
