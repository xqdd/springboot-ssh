package ${groupId}.web.configuration;

import ${groupId}.mvc.bean.entity.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.UiConfiguration;
import springfox.documentation.swagger.web.UiConfigurationBuilder;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Configuration
@EnableSwagger2
public class Swagger {

    private static final String PROJECT_NAME = "驾考项目";
    private static final String VERSION = "1.0.0";
    private final Class[] ignoreClasses = new Class[]{
            User.class,
            HttpSession.class,
            HttpServletRequest.class,
            HttpServletResponse.class,
    };

    @Bean
    public Docket miniapp() {
        return generateDocket("小程序端", "${groupId}.mvc.controller.miniapp");
    }


    @Bean
    public Docket admin() {
        return generateDocket("管理端", "${groupId}.mvc.controller.admin");
    }


    private Docket generateDocket(String groupName, String controllerPackage) {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName(groupName)
                .enable(true)
                .select()
                .apis(RequestHandlerSelectors.basePackage(controllerPackage))
                .build()
                .directModelSubstitute(ResponseEntity.class, Void.class)
                .directModelSubstitute(Object.class, Void.class)
                .directModelSubstitute(PageRequest.class, Void.class)
                .useDefaultResponseMessages(false)
                .ignoredParameterTypes(ignoreClasses)
                .apiInfo(new ApiInfoBuilder()
                        .title(PROJECT_NAME + "-" + groupName)
                        .version(VERSION)
                        .build());
    }

    @Bean
    UiConfiguration uiConfig() {
        return UiConfigurationBuilder.builder()
                .defaultModelExpandDepth(5)
                .build();
    }

}