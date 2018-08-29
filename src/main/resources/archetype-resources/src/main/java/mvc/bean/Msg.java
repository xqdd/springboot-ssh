package ${groupId}.mvc.bean;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import org.springframework.util.Assert;

import java.util.HashMap;
import java.util.Map;

@JsonInclude(Include.NON_NULL)
public class Msg {


    public static Integer CODE_FAILED = 0;
    public static Integer CODE_SUCCESS = 1;
    public static Integer CODE_AUTHORITY = -1;

    private Integer code;
    private Object data;
    private String msg;
    private String debugMsg;


    public Msg(String msg) {
        this.msg = msg;
    }


    public Msg() {
    }

    public Msg(Integer code, Object data) {
        this.data = data;
        this.code = code;
    }

    public Msg(Integer code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public Msg(Integer code, String msg, String debugMsg) {
        this.code = code;
        this.msg = msg;
        this.debugMsg = debugMsg;
    }

    private Msg(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Msg(Integer code) {
        this.code = code;
    }

    public static ${groupId}.mvc.bean.Msg failedDetail(String msg, Object errors) {
        return new ${groupId}.mvc.bean.Msg(CODE_FAILED, msg, errors);
    }

    public static ${groupId}.mvc.bean.Msg failed() {
        return new ${groupId}.mvc.bean.Msg(CODE_FAILED);
    }

    public static ${groupId}.mvc.bean.Msg success() {
        return new ${groupId}.mvc.bean.Msg(CODE_SUCCESS);
    }

    public String getDebugMsg() {
        return debugMsg;
    }

    public void setDebugMsg(String debugMsg) {
        this.debugMsg = debugMsg;
    }


    public static ${groupId}.mvc.bean.Msg success(String msg) {
        return new ${groupId}.mvc.bean.Msg(CODE_SUCCESS, msg);
    }


    public void setData(Object data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }


    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }


    public static ${groupId}.mvc.bean.Msg failed(String msg) {
        return new ${groupId}.mvc.bean.Msg(CODE_FAILED, msg);
    }

    public static ${groupId}.mvc.bean.Msg authority(String msg) {
        return new ${groupId}.mvc.bean.Msg(CODE_AUTHORITY, msg);
    }

    public static ${groupId}.mvc.bean.Msg failedAndDebug(String msg, String debugMsg) {
        return new ${groupId}.mvc.bean.Msg(CODE_FAILED, msg, debugMsg);
    }

    public static ${groupId}.mvc.bean.Msg authorityAndDebug(String msg, String debugMsg) {
        return new ${groupId}.mvc.bean.Msg(CODE_AUTHORITY, msg, debugMsg);
    }


    public static ${groupId}.mvc.bean.Msg successDataMsg(String... data) {
        return new ${groupId}.mvc.bean.Msg(CODE_SUCCESS, data2Map(data));
    }


    public static ${groupId}.mvc.bean.Msg failedDataMsg(String... data) {
        return new ${groupId}.mvc.bean.Msg(CODE_FAILED, data2Map(data));
    }


    public static ${groupId}.mvc.bean.Msg successData(Object data) {
        return new ${groupId}.mvc.bean.Msg(CODE_SUCCESS, data);
    }

    public static ${groupId}.mvc.bean.Msg failed(Object data) {
        return new ${groupId}.mvc.bean.Msg(CODE_FAILED, data);
    }


    private static Map<String, String> data2Map(String[] arguments) {
        int length = arguments.length;
        Assert.isTrue(length % 2 == 0, "传入的参数个数必须为偶数");
        HashMap<String, String> map = new HashMap<>();

        for (int i = 0; i < length / 2; ++i) {
            map.put(arguments[i], arguments[i + length / 2]);
        }
        return map;
    }
}
