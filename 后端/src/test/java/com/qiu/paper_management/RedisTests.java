package com.qiu.paper_management;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.util.concurrent.TimeUnit;

// 将先启动springboot容器，这样才能取到Redis对象
@SpringBootTest
public class RedisTests {
    @Autowired
    private StringRedisTemplate redis;

    @Test
    // 为保证setter先执行，改个好名字
    public void a_set(){
        ValueOperations<String, String> operations = redis.opsForValue();
        operations.set("username", "邱剑荣");
        operations.set("token", "1234567", 10, TimeUnit.SECONDS);
        System.out.println("this is setter");
    }
    @Test
    public void b_get(){
        ValueOperations<String, String> operations = redis.opsForValue();
        System.out.println(operations.get("username"));
        System.out.println(operations.get("token"));
    }
}
