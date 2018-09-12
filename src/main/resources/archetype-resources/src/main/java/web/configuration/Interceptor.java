package ${groupId}.web.configuration;


import ${groupId}.web.interceptor.AdminInterceptor;
import ${groupId}.web.interceptor.UserInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class Interceptor implements WebMvcConfigurer {

    private final UserInterceptor userTokenInterceptor;

    private final AdminInterceptor adminInterceptor;

    @Autowired
    public Interceptor(UserInterceptor userTokenInterceptor, AdminInterceptor adminInterceptor) {
        this.userTokenInterceptor = userTokenInterceptor;
        this.adminInterceptor = adminInterceptor;
    }


    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(this.userTokenInterceptor)
                //登录
                .excludePathPatterns("/wx/miniAppLogin**")
                .excludePathPatterns("/wx/fakeLogin")
                //通用接口
                .excludePathPatterns("/comm/**")
                .addPathPatterns("/comm/imageUpload")
                //swagger
                .excludePathPatterns("/swagger*")
                .excludePathPatterns("/swagger*/**")
                .excludePathPatterns("/v2/**")
                .excludePathPatterns("/webjars/**")
                //admin
                .excludePathPatterns("/admin/**")
//TODO 加上通配符
                .addPathPatterns("/");


        registry.addInterceptor(this.adminInterceptor)
                .excludePathPatterns("/admin/login")
                .excludePathPatterns("/admin/getCaptcha")
                .addPathPatterns("/admin/");
    }
}
