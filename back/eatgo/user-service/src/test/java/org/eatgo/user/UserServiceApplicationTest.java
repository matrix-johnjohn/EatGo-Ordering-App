package org.eatgo.user;

import cn.hutool.json.JSONUtil;
import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTUtil;
import org.eatgo.common.domain.dto.LoginDto;
import org.eatgo.common.domain.po.User;
import org.eatgo.common.domain.vo.ResultVo;
import org.eatgo.user.mapper.UserMapper;
import org.eatgo.user.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;

@SpringBootTest
public class UserServiceApplicationTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserMapper userMapper;

    @Test
    void register(){
        ResultVo<String>vo=userService.login(
                new LoginDto("kimo",
                        "123456",
                        "1596903229@qq.com",
                        ""));
        System.out.println(vo);
    }

    @Test
    void login(){
        LoginDto dto=new LoginDto("kimo",
                "123456",
                "1596903229@qq.com",
                "2a631a");

        ResultVo<String> login=userService.login(dto);

        System.out.println(login);
    }

    @Test
    void resetPassword(){
        LoginDto dto=new LoginDto("",
                "041367mat",
                "1596903229@qq.com",
                "58da88");

        ResultVo<String>vo=userService.resetPassword(dto);
        System.out.println(vo);
    }

}
