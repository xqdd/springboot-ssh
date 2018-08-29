package ${groupId}.web.interceptor;

import ${groupId}.mvc.bean.Msg;
import ${groupId}.utils.CommUtils;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import org.apache.tomcat.util.http.fileupload.servlet.ServletRequestContext;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class CommInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (request != null && ServletFileUpload.isMultipartContent(request)) {
            long maxSize = 20 * 1024 * 1024;
            if (new ServletRequestContext(request).contentLength() > maxSize) {
                CommUtils.responseJson(response, Msg.failed("请先登录"));
                return false;
            }
        }
        return true;
    }
}
