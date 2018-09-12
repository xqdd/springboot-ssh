package ${groupId}.utils.wx.bean.miniapp;

import lombok.Data;

@Data
public class WxMiniAppUserInfo {
    private String openId;

    private String nickName;

    private String gender;

    private String language;

    private String city;

    private String province;

    private String country;

    private String avatarUrl;

    private String unionId;

    private Watermark watermark;



    @Data
    public class Watermark {
        private String timestamp;

        private String appid;

    }
}
