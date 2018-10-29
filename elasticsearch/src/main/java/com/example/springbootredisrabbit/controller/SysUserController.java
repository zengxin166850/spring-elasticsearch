package com.example.springbootredisrabbit.controller;

import com.example.springbootredisrabbit.po.Result;
import com.example.springbootredisrabbit.po.SysUser;
import com.example.springbootredisrabbit.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SysUserController {

    @Autowired
    SysUserService service;

    @GetMapping("/user")
    public Result<SysUser> findone(String id) throws Exception {
        return new Result<SysUser>().success(service.findone(id));
    }
}
