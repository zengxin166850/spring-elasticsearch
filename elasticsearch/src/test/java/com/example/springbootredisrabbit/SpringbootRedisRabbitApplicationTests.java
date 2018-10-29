package com.example.springbootredisrabbit;

import com.example.springbootredisrabbit.service.SysUserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringbootRedisRabbitApplicationTests {
    @Autowired
    SysUserService service;
    @Autowired
    RedisTemplate redisTemplate;

    @Test
    public void contextLoads() {
    }
    @Test
    public void findone() throws Exception {
        List<String> list = new ArrayList<>();
        list.add("111");
        list.add("211");
        list.add("123");
        list.add("456");
        list.add("234");
        list.add("126");
//        redisTemplate.opsForList().leftPushAll("test",list);
        redisTemplate.expire("test",10,TimeUnit.SECONDS);
        Object t = redisTemplate.opsForList().index("test",3);
        System.out.println(t);
    }
}
