package ${groupId}.utils.wx.bean;

import lombok.Data;

@Data
public class WxToken {

    private String access_token;
    private String refresh_token;
    private String openid;
    private String expires_in;
    private String scope;
    private String unionid;


}
