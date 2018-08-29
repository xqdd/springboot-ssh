package ${groupId}.utils.wx.bean;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class WxUser  implements Serializable {
    private String openid;
    private String nickname;
    private String sex;
    private String province;
    private String city;
    private String country;
    private String headimgurl;
    //后面三个不用保存，前面随意
    private List<String> privilege;
    private String unionid;
    private String language;


}
