package ${groupId}.utils.wx.bean.miniapp;

import lombok.Data;

@Data
public class WxMiniAppLoginInfo {
    //用户唯一标识
    private String openid;
    //会话密钥
    private String session_key;
    //用户在开放平台的唯一标识符
    private String unionid;



}
