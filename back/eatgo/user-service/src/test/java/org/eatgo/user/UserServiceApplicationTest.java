package org.eatgo.user;

import org.eatgo.common.domain.dto.LoginDto;
import org.eatgo.common.domain.vo.ResultVo;
import org.eatgo.user.client.EmailClient;
import org.eatgo.user.mapper.UserMapper;
import org.eatgo.user.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserServiceApplicationTest {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private EmailClient emailClient;

    @Autowired
    private UserService userService;
    @Test
    public void test1(){
        userMapper.insertUser(new LoginDto("kimo","123456","1596903229@qq.com","123456"));
    }

    @Test
    public void test2(){
        ResultVo<String> res=emailClient.sendEmail(new LoginDto("kimo", "123456", "dksjfkjlas", "123456"));

        System.out.println(res);
    }

    @Test
    public void test3(){
        ResultVo<String> vo = userService.sendEmail(new LoginDto("kimo",
                "123456",
                "1596903229@qq.com",
                "123456"));

        System.out.println(vo);
    }

}
