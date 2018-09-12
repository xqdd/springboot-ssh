package ${groupId}.utils.wx;

import com.fasterxml.jackson.databind.ObjectMapper;
import ${groupId}.utils.wx.bean.WxToken;
import ${groupId}.utils.wx.bean.WxUser;
import ${groupId}.utils.wx.bean.miniapp.WxMiniAppEncryption;
import ${groupId}.utils.wx.bean.miniapp.WxMiniAppLoginInfo;
import ${groupId}.utils.wx.bean.miniapp.WxMiniAppUserInfo;
import lombok.extern.slf4j.Slf4j;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.encoders.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.AlgorithmParameters;
import java.security.Security;
import java.util.Arrays;
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
            p.load(WxOauth2Utils.class.getClassLoader().getResourceAsStream("wx.properties"));
            url = url.replaceAll("APPID", p.getProperty("appid"));
            url = url.replaceAll("SECRET", p.getProperty("appsecret"));
            url = url.replaceAll("CODE", code);

            jsonText = SSLUtils.httpsGet(new URI(url));

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
    public static WxUser get_wx_user(WxToken wxToken) {
        String url = "https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";
        String jsonText = "";
        try {
            url = url.replaceAll("ACCESS_TOKEN", wxToken.getAccess_token());
            url = url.replaceAll("OPENID", wxToken.getOpenid());

            jsonText = SSLUtils.httpsGet(new URI(url));
            return new ObjectMapper().readValue(jsonText, WxUser.class);
        } catch (URISyntaxException | IOException e) {
            log.warn("获取用户信息失败：" + jsonText, e);
        }
        return null;
    }


    /**
     * 用code获取小程序登录信息
     *
     * @param code
     * @return
     */
    public static Object login_mini_app(String code) {
        String url = "https://api.weixin.qq.com/sns/jscode2session?appid=APPID&secret=SECRET&js_code=JSCODE&grant_type=authorization_code";
        String jsonText = "";
        Properties p = new Properties();
        try {
            p.load(WxOauth2Utils.class.getClassLoader().getResourceAsStream("wx.properties"));
            url = url.replaceAll("APPID", p.getProperty("mini_appid"));
            url = url.replaceAll("SECRET", p.getProperty("mini_appsecret"));
            url = url.replaceAll("JSCODE", code);
            jsonText = SSLUtils.httpsGet(new URI(url));
            return new ObjectMapper().readValue(jsonText, WxMiniAppLoginInfo.class);
        } catch (URISyntaxException | IOException e) {
            log.error("登录小程序失败：" + jsonText, e);
            return jsonText;
        }
    }


    public static WxMiniAppUserInfo decryptData(WxMiniAppEncryption encryption, WxMiniAppLoginInfo loginInfo) {
        //加密密匙
        byte[] keyByte = Base64.decode(loginInfo.getSession_key());
        //偏移量
        byte[] ivByte = Base64.decode(encryption.getIv());
        //被加密的数据
        byte[] dataByte = Base64.decode(encryption.getEncryptedData());

        try {
            // 如果密钥不足16位，那么就补足.  这个if 中的内容很重要
            int base = 16;
            if (keyByte.length % base != 0) {
                int groups = keyByte.length / base + (keyByte.length % base != 0 ? 1 : 0);
                byte[] temp = new byte[groups * base];
                Arrays.fill(temp, (byte) 0);
                System.arraycopy(keyByte, 0, temp, 0, keyByte.length);
                keyByte = temp;
            }
            // 初始化
            Security.addProvider(new BouncyCastleProvider());
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding", "BC");
            SecretKeySpec spec = new SecretKeySpec(keyByte, "AES");
            AlgorithmParameters parameters = AlgorithmParameters.getInstance("AES");
            parameters.init(new IvParameterSpec(ivByte));
            cipher.init(Cipher.DECRYPT_MODE, spec, parameters);// 初始化
            byte[] resultByte = cipher.doFinal(dataByte);
            if (null != resultByte && resultByte.length > 0) {
                return new ObjectMapper().readValue(new String(resultByte, "UTF-8"), WxMiniAppUserInfo.class);
            }
            log.warn("解密出的小程序用户信息为空");
            return null;
        } catch (Exception e) {
            log.error("解密小程序用户信息失败", e);
            return null;
        }
    }


}


