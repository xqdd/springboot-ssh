package ${groupId}.web.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class AdminInterceptor extends HandlerInterceptorAdapter{


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (request.getSession().getAttribute("admin") == null) {
            response.setContentType("application/json; charset=utf-8");
            response.getWriter().print((new ObjectMapper()).writeValueAsString(${groupId}.bean.Msg.failed("msg", "请先登录")));
            return false;
        } else {
            return true;
        }
    }
}
