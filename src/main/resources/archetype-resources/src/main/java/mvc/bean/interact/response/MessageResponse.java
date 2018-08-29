package ${groupId}.mvc.bean.interact.response;

import lombok.Data;

@Data
//发送短信回复bean
public class MessageResponse {
    private Integer status;
    private String msg;
    private Result result;


    @Data
    class Result {
        private String count;
        private String accountid;
        private String msgid;
    }
}
