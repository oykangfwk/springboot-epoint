package org.example.controller;

import io.swagger.annotations.ApiOperation;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.example.bean.Car;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RestController
@Slf4j
public class HelloController {

    @Autowired
    Car car;

    @ApiOperation("测试Swagger注释作用")
    @GetMapping("/getcar")
    public Car getCar(){
        return car;
    }

    @GetMapping("/hello")
    public String hello(){
        log.info("lombok-test");
        return "hello,springboot";
    }
}
