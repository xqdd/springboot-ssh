package ${groupId}.utils.wx;

import com.fasterxml.jackson.databind.ObjectMapper;
import ${groupId}.utils.wx.bean.WxToken;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Properties;

@Slf4j
public class WxOauth2Utils {

    /**
     * 使用code兑换access_token
     *
     * @param code
     * @return
     */
    public static WxToken get_access_token(String code) {
        String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";
        Properties p = new Properties();
        String jsonText = "";
        try {
            p.load(${groupId}.utils.wx.WxOauth2Utils.class.getClassLoader().getResourceAsStream("wx.properties"));
            url = url.replaceAll("APPID", p.getProperty("appid"));
            url = url.replaceAll("SECRET", p.getProperty("appsecret"));
            url = url.replaceAll("CODE", code);

            jsonText = ${groupId}.utils.wx.SSLUtils.httpsGet(new URI(url));

            return new ObjectMapper().readValue(jsonText, WxToken.class);
        } catch (URISyntaxException | IOException e) {
            log.warn("获取登录access_token失败：" + jsonText, e);
            return null;
        }
    }


    /**
     * 使用access_token和open_id拉取用户信息
     *
     * @param wxToken
     * @return
     */
    public static ${groupId}.utils.wx.bean.WxUser get_wx_user(WxToken wxToken) {
        String url = "https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";
        String jsonText = "";
        try {
            url = url.replaceAll("ACCESS_TOKEN", wxToken.getAccess_token());
            url = url.replaceAll("OPENID", wxToken.getOpenid());

            jsonText = ${groupId}.utils.wx.SSLUtils.httpsGet(new URI(url));
            return new ObjectMapper().readValue(jsonText, ${groupId}.utils.wx.bean.WxUser.class);
        } catch (URISyntaxException | IOException e) {
            log.warn("获取用户信息失败：" + jsonText, e);
        }
        return null;
    }


}


