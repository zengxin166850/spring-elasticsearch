package com.example.springbootredisrabbit.mapper;

import com.example.springbootredisrabbit.po.SysUser;
import org.apache.ibatis.annotations.Select;

public interface SysUserMapper {

    @Select("select * from sys_user where id = #{id}")
    SysUser findone(String id);
}
