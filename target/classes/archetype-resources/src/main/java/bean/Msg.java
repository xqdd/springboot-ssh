package ${groupId}.bean;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonView;
import ${groupId}.utils.jsonview.Group;
import org.springframework.util.Assert;

import java.util.HashMap;
import java.util.Map;

@JsonInclude(Include.NON_NULL)
public class Msg {
    @JsonView({Group.List1.class, Group.List2.class})
    private Integer code;
    @JsonView({Group.List1.class, Group.List2.class})
    private Object data;
    @JsonView({Group.List1.class, Group.List2.class})
    private String msg;
    @JsonView({Group.List1.class, Group.List2.class})
    private Map<String, String> errors;
    public static final Integer CODE_FAILED = 0;
    public static final Integer CODE_SUCCESS = 1;
    @JsonView({Group.List1.class, Group.List2.class})
    private Integer totalPage;
    @JsonView({Group.List1.class, Group.List2.class})
    private Integer pageSize;
    @JsonView({Group.List1.class, Group.List2.class})
    private Integer currPage;
    @JsonView({Group.List1.class, Group.List2.class})
    private Long totalCount;


    public Long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Long totalCount) {
        this.totalCount = totalCount;
    }

    public Map<String, String> getErrors() {
        return this.errors;
    }

    public void setErrors(Map<String, String> errors) {
        this.errors = errors;
    }

    public Integer getTotalPage() {
        return this.totalPage;
    }

    public void setTotalPage(Integer totalPage) {
        this.totalPage = totalPage;
    }

    public Integer getCode() {
        return this.code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Object getData() {
        return this.data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getMsg() {
        return this.msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Msg(Integer code) {
        this.code = code;
    }

    public Msg(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Msg(Integer code, Object data) {
        this.code = code;
        this.data = data;
    }

    public Msg(Integer code, Object data, Long totalCount, Integer totalPage, Integer pageSize, Integer currPage) {
        this.code = code;
        this.pageSize = pageSize;
        this.currPage = currPage;
        this.data = data;
        this.totalPage = totalPage;
        this.totalCount = totalCount;
    }

    public Integer getPageSize() {
        return this.pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getCurrPage() {
        return this.currPage;
    }

    public void setCurrPage(Integer currPage) {
        this.currPage = currPage;
    }

    public Msg(Integer code, Map<String, String> errors) {
        this.code = code;
        this.errors = errors;
    }

    public static Msg failed(String... arguments) {
        int length = arguments.length;
        Assert.isTrue(length % 2 == 0, "传入的参数个数必须为偶数");
        HashMap<String, String> map = new HashMap<>();

        for (int i = 0; i < length / 2; ++i) {
            map.put(arguments[i], arguments[i + length / 2]);
        }

        return new Msg(CODE_FAILED, map);
    }

    public static Msg success(String msg) {
        return new Msg(CODE_SUCCESS, msg);
    }

    public static Msg successData(Object object) {
        return Msg.success(object);
    }

    public static Msg success(Object object) {
        return new Msg(CODE_SUCCESS, object);
    }

    public static Msg success(Object data, Long totalCount, Integer totalPage, Integer pageSize, Integer currPage) {
        return new Msg(CODE_SUCCESS, data, totalCount, totalPage, pageSize, currPage);
    }
}
