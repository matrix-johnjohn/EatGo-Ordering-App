package org.eatgo.user;

import org.eatgo.common.domain.dto.LoginDto;
import org.eatgo.user.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserServiceApplicationTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void test1(){
        userMapper.insertUser(new LoginDto("kimo","123456","1596903229@qq.com","123456"));
    }

}
