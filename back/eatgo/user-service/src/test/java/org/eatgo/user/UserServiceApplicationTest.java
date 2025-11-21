package org.eatgo.user;

import cn.hutool.json.JSONUtil;
import org.eatgo.common.domain.dto.FundDto;
import org.eatgo.common.domain.dto.LoginDto;
import org.eatgo.common.domain.po.User;
import org.eatgo.user.mapper.UserMapper;
import org.eatgo.user.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.List;

@SpringBootTest
public class UserServiceApplicationTest {

    private final FundDto balanceDto=new FundDto("1596903229@qq.com",20,1);

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private JedisPool jedisPool;

    @Test
    public void test1(){
        User user=new User();
        user.setId(18);
        user.setUsername("admin");
        user.setPassword("123456");
        user.setAvatar("http://192.168.174.130:9000/eatgo/avatar/1.jpg");
        user.setEmail("matrix@skyhub.com");
        userMapper.EditUser(user);
    }

    @Test
    public void test2(){
        User user=new User();
        user.setId(17);
        user.setIsEffective(1);
        userService.updateUserEffective(user);
    }

    @Test
    public void test3(){
        List<User> list=userService.list();

        Jedis jedis=jedisPool.getResource();
        for (User user : list) {
            String json=JSONUtil.toJsonStr(user);

            jedis.set("user:info:"+user.getId(),json);
        }
        jedis.close();
    }

}
