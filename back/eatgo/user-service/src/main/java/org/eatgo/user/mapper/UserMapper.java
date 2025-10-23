package org.eatgo.user.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.eatgo.common.domain.dto.LoginDto;

@Mapper
public interface UserMapper {

    @Insert("insert into user (username,password,email) values (#{username},#{password},#{email})")
    public void register(LoginDto user);//注册加入数据
}
