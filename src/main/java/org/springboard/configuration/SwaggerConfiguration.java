package org.springboard.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

@Configuration
@EnableSwagger2
public class SwaggerConfiguration {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .paths(PathSelectors.any())
                .build()
                .apiInfo(getApiInfo())
                ;
    }

    private ApiInfo getApiInfo() {
        return new ApiInfo(
                "SpringBoard API",
                "Documents Powered by Swagger 2",
                "API V1.0",
                "http://localhost:8888",
                new Contact("@CbdFocus", "https://twitter.com/CbdFocus", "wwwicbd@gmail.com"),
                "MIT License",
                "https://raw.githubusercontent.com/icbd/SpringBoard/develop/LICENSE",
                Collections.emptyList());
    }
}
