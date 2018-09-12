package ${groupId}.web.interceptor;

import ${groupId}.mvc.bean.interact.response.Result;
import ${groupId}.mvc.service.UserService;
import ${groupId}.utils.CommUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Component
public class UserInterceptor extends HandlerInterceptorAdapter {


    private final HttpSession session;
    private final UserService userService;

    @Autowired
    public UserInterceptor(HttpSession session, UserService userService) {
        this.session = session;
        this.userService = userService;
    }


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (session.getAttribute("user") == null) {
//            session.setAttribute("user", userService.findById("1").get());
//            return true;
            CommUtils.responseJson(response, Result.error("请先登录"), 401);
            return false;
        } else {
            return true;
        }
    }
}
