package com.example.springbootredisrabbit;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = {"com.example.springbootredisrabbit.mapper.**"})
public class SpringbootRedisRabbitApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootRedisRabbitApplication.class, args);
    }

}
