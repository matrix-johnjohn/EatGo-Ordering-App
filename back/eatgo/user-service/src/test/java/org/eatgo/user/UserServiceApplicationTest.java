package org.eatgo.user;

import org.eatgo.common.domain.dto.FundDto;
import org.eatgo.user.mapper.UserMapper;
import org.eatgo.user.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import redis.clients.jedis.JedisPool;

@SpringBootTest
public class UserServiceApplicationTest {

    private final FundDto balanceDto=new FundDto("1596903229@qq.com",20,1);

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private JedisPool jedisPool;

}
