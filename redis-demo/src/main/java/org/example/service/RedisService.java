package org.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class RedisService {

    @Autowired
    RedisTemplate redisTemplate;

    public Set getSet(String name){
        RedisConnection connection = redisTemplate.getConnectionFactory().getConnection();
        System.out.println("ping=="+connection.ping());
        Set members = redisTemplate.opsForSet().members(name);
        return members;
    }
}
