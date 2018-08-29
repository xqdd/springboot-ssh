package ${groupId}.web.configuration;


import ${groupId}.web.interceptor.UserTokenInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class Interceptor implements WebMvcConfigurer {

    private final UserTokenInterceptor userTokenInterceptor;

    @Autowired
    public Interceptor(UserTokenInterceptor userTokenInterceptor) {
        this.userTokenInterceptor = userTokenInterceptor;
    }


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(this.userTokenInterceptor)
                //登录
                .excludePathPatterns("/user/login/**")
                //活动
                .excludePathPatterns("/activity/get*")
                //通用接口
                .excludePathPatterns("/comm/**")
                .addPathPatterns("/comm/imageUpload")
                //swagger
                .excludePathPatterns("/swagger*")
                .excludePathPatterns("/swagger*/**")
                .excludePathPatterns("/v2/**")
                .excludePathPatterns("/webjars/**")
                .addPathPatterns("/**");
    }
}
