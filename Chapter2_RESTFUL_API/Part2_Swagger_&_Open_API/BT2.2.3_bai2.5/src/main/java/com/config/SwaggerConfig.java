package com.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * OpenAPI / Swagger configuration for Project 2.5.
 * This keeps the same main ideas as the referenced article:
 * User API grouping, custom API metadata, and a custom Swagger UI path.
 */
@Configuration
public class SwaggerConfig {

    @Bean
    public GroupedOpenApi userApi() {
        return GroupedOpenApi.builder()
                .group("User API")
                .packagesToScan("com.controller")
                .pathsToMatch("/users/**")
                .build();
    }

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Spring Boot & OpenAPI")
                        .description("Sample Spring Boot RESTful service documented with OpenAPI 3.0 / Swagger UI")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Project 2.5")
                                .url("https://github.com/essentialprogramming/spring-boot-openapi")));
    }
}
