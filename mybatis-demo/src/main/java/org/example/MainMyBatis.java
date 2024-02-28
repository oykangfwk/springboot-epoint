package org.example;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.concurrent.TimeUnit;

@SpringBootApplication
@MapperScan("org.example.mapper")
public class MainMyBatis {
    public static void main(String[] args) {
        SpringApplication.run(MainMyBatis.class,args);
    }
}