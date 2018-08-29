package ${groupId}.utils;


import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.http.MediaType;

import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;

@Slf4j
public class CommUtils {


    //年月日期格式化
    public final static SimpleDateFormat DATE_YEAR_MOUTH_FORMAT = new SimpleDateFormat("yyyyMM");
    //普通日期格式化
    public final static SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
    //精确到微秒日期时间格式化
    public final static SimpleDateFormat DATETIME_FORMAT_WITH_MS = new SimpleDateFormat("yyyy年MM月dd号H时m分s.sss秒");

    public final static GregorianCalendar calendar = new GregorianCalendar();


    //代理请求头字段
    private static String headerIpFields[] = {"x-forwarded-for", "PRoxy-Client-IP", "WL-Proxy-Client-IP", "HTTP_CLIENT_IP", "HTTP_X_FORWARDED_FOR", "X-Real-IP"};

    /**
     * 获取id地址，透明代理也能看出来
     *
     * @param request 请求
     * @return ip地址
     */
    public static String getRemoteIp(HttpServletRequest request) {
        //正常获取ip地址
        String ip = request.getRemoteAddr();
        if (isIpValid(ip)) {
            return ip;
        }
        //获取代理中的ip地址
        for (String headerIpField : headerIpFields) {
            ip = request.getHeader(headerIpField);
            if (isIpValid(ip)) {
                return ip;
            }
        }
        return null;
    }

    /**
     * 判断ip是否有效
     *
     * @param ip ip地址
     * @return 是否有效
     */
    private static boolean isIpValid(String ip) {
        return !(StringUtils.isBlank(ip) || "unknown".equalsIgnoreCase(ip));
    }


    /**
     * 发送短信验证码
     *
     * @param phoneNUmber 手机号
     * @param vCode       验证码
     * @return 接口返回数据
     */
    public static ${groupId}.mvc.bean.interact.response.MessageResponse sendPhoneVCode(String phoneNUmber, String vCode) {
        try {
            String messageTemplate = "http://api.jisuapi.com/sms/send?mobile=MOBILE&content=您的手机验证码是@，5分钟内有效。如非本人操作请忽略本短信。【西柚网络】&appkey=秘密";
            messageTemplate = messageTemplate.replaceAll("@", vCode);
            messageTemplate = messageTemplate.replaceAll("MOBILE", phoneNUmber);
            HttpGet httpGet = new HttpGet(messageTemplate);
            CloseableHttpResponse closeableHttpResponse = HttpClients.createDefault().execute(httpGet);
            ${groupId}.mvc.bean.interact.response.MessageResponse response = new ObjectMapper().readValue(EntityUtils.toString(closeableHttpResponse.getEntity()), ${groupId}.mvc.bean.interact.response.MessageResponse.class);
            closeableHttpResponse.close();
            log.info("发送短信验证码："+response);
            return response;
        } catch (Exception e) {
            log.error("发送短信验证码失败", e);
            return null;
        }
    }


    private static String strs = "0123456789qwertyuiopasdfghjklzxcvbnmQWERTYUIOPASDFGHJKLZXCVBNM";

    /**
     * 随机字符串
     *
     * @param length 长度
     * @param type   生成类型
     * @return 生成结果
     */
    public static String randomString(int length, RandomType type) {
        StringBuilder sb = new StringBuilder();
        switch (type) {
            case NUMBER:
                for (int i = 0; i < length; ++i) {
                    sb.append(strs.charAt((int) (Math.random() * 10)));
                }
                break;
            case LOWER:
                for (int i = 0; i < length; ++i) {
                    sb.append(strs.charAt((int) (Math.random() * strs.length())));
                }
                break;
            default:
                for (int i = 0; i < length; ++i) {
                    sb.append(strs.charAt((int) (Math.random() * 36)));
                }
        }
        return sb.toString();
    }

    public static enum RandomType {
        NUMBER,
        LOWER,
        ALL
    }


    /**
     * 生成随机数字
     *
     * @param length 长度
     * @return 生成的字符串
     */
    public static String randomNumber(int length) {
        return randomString(length, RandomType.NUMBER);
    }


    /**
     * 返回json数据
     * @param response HttpResponse
     * @param content 要显示的内容
     * @throws IOException HttpResponse有误
     */
    public static void responseJson(ServletResponse response, Object content) throws IOException {
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        response.getWriter().print((new ObjectMapper()).writeValueAsString(content));
    }
}
