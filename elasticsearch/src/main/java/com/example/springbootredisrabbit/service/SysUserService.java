package com.example.springbootredisrabbit.service;

import com.example.springbootredisrabbit.mapper.SysUserMapper;
import com.example.springbootredisrabbit.po.CommonUtil;
import com.example.springbootredisrabbit.po.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.serializer.Serializer;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class SysUserService {
    @Autowired
    SysUserMapper sysUserMapper;

    @Autowired
    RedisTemplate redisTemplate;

    public SysUser findone(String id) throws Exception {
        SysUser sysUser = null;
        byte[] user_test = (byte[])redisTemplate.opsForValue().get("user_test");
        if (user_test != null) {
            return (SysUser) CommonUtil.unserizlize(user_test);
        } else {
            sysUser = sysUserMapper.findone(id);
            redisTemplate.opsForValue().set("user_test", CommonUtil.serialize(sysUser));
        }

        return sysUser;
    }


}
