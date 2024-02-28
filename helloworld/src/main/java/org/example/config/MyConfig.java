package org.example.config;

import org.example.bean.Car;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;

@Configuration
@EnableConfigurationProperties(value = Car.class) //开启ConfigurationProperties注解，并将Car类加入容器
@EnableSwagger2 //开启swagger
public class MyConfig {

    //定义docket
    @Bean
    public Docket docket() {
        return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo()).enable(true).groupName("Test").
                // 通过.select()方法，去配置扫描接口,RequestHandlerSelectors配置如何扫描
                select().apis(RequestHandlerSelectors.basePackage("org.example.controller")).paths(PathSelectors.any()).build();
    }

    private ApiInfo apiInfo() {
        Contact contact = new Contact("oykang","oykang@163","oykang@163.com");
        return new ApiInfo("Swagger2学习","学习如何配置swagger","V1","www.oykang.com",contact,"Apach 2.0许可","许可链接",
                new ArrayList<>());
    }


}
