package org.eatgo.user.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.eatgo.common.domain.dto.LoginDto;
import org.eatgo.common.domain.po.User;

import java.util.List;

@Mapper
public interface UserMapper {

    @Insert("insert into user (username,password,email) values (#{username},#{password},#{email})")
    public void register(LoginDto user); //注册加入数据

    @Select("select * from user where email=#{email} and password=#{password}")
    public User login(LoginDto dto); //登录

    @Update("update user set password=#{password} where email=#{email}")
    public void resetPassword(LoginDto dto); //重设密码

    public void EditUser(User user); //更改用户身份信息

    @Select("select * from user")
    public List<User> list();// 用户列表

    @Select("select * from user where email=#{email}")
    public User findByEmail(String email);

    @Select("select * from user")
    public List<User>UserList();


    public void updateUserEffective(User user);
}
