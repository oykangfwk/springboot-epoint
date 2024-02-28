package org.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SwaggerConfig {

    @Bean
    public Docket docket() {
        return new Docket(DocumentationType.SWAGGER_2).groupName("webApi").apiInfo(apiInfo()).select().build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder().title("rabbitmq文档").description("本文档描述mq接口").version("1.0").
                contact(new Contact("oyk","http://localhost:8080","oykang@123.com")).build();
    }
}
