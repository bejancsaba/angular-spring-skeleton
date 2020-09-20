package com.example.demo.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.nio.file.Paths;
import java.util.function.Predicate;

import static springfox.documentation.builders.PathSelectors.regex;

@Configuration
public class SwaggerConfig {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("Agular - Spring - Skeleton")
                .apiInfo(serviceApiInfo())
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo serviceApiInfo() {
        return new ApiInfoBuilder()
                .title("Demo for angular-spring-skeleton REST API")
                .build();
    }

    @Bean
    public Docket actuatorDocket(@Value("${management.endpoints.web.base-path}") String managementContextPath) {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("Actuator")
                .apiInfo(actuatorApiInfo())
                .select().paths(managementPath(managementContextPath))
                .build();
    }

    private ApiInfo actuatorApiInfo() {
        return new ApiInfoBuilder()
                .title("Actuator REST API")
                .build();
    }

    private Predicate<String> managementPath(String managementContextPath) {
        return regex(managementPathRegex(managementContextPath));
    }

    private String managementPathRegex(String managementContextPath) {
        return Paths.get(managementContextPath, ".*").toString();
    }
}
