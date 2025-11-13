package com.example.demo.mapper;

import com.example.demo.po.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface DemoMapper {

    @Insert("insert into test (username,gender) values (#{username},#{gender})")
    public void insertUser(@Param("username")String username, @Param("gender")String gender);
}
