package org.example.controller;

import org.example.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
public class RedisController {

    @Autowired
    RedisService redisService;

    @GetMapping("/getset/{name}")
    public String getSetMems(@PathVariable("name") String name){
        Set<String> set = redisService.getSet(name);
        return set.stream().collect(Collectors.toList()).toString();
    }
}
