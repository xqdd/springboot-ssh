package ${groupId}.web.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class Swagger {

    public static final String SWAGGER_SCAN_BASE_PACKAGE = "${groupId}.mvc.controller";
    public static final String VERSION = "1.0.0";


    @Bean
    public Docket customImplementation() {
        ApiInfo apiInfo = new ApiInfoBuilder()
                .title("技能项目")
                .version(VERSION)
                .build();


        return new Docket(DocumentationType.SWAGGER_2)
                .enable(true)
                .select()
                .apis(RequestHandlerSelectors.basePackage(SWAGGER_SCAN_BASE_PACKAGE))
                .build()
                .directModelSubstitute(ResponseEntity.class, Void.class)
                .directModelSubstitute(Object.class, Void.class)
                .directModelSubstitute(PageRequest.class, Void.class)
                .useDefaultResponseMessages(false)
                .apiInfo(apiInfo);
    }
}