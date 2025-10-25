package org.eatgo.user.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.eatgo.common.domain.dto.LoginDto;
import org.eatgo.common.domain.po.User;

@Mapper
public interface UserMapper {

    @Insert("insert into user (username,password,email) values (#{username},#{password},#{email})")
    public void register(LoginDto user);//注册加入数据

    @Select("select * from user where email=#{email} and password=#{password}")
    public User login(LoginDto dto);//登录

    @Update("update user set password=#{password} where email=#{email}")
    public void resetPassword(LoginDto dto);//重设密码
}
