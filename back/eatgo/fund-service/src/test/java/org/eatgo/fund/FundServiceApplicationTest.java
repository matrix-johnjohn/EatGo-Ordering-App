package org.eatgo.fund;

import cn.hutool.json.JSONUtil;
import org.eatgo.common.domain.dto.FundDto;
import org.eatgo.common.domain.po.User;
import org.eatgo.fund.mapper.FundMapper;
import org.eatgo.fund.service.FundService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@SpringBootTest
public class FundServiceApplicationTest {

    @Autowired
    private JedisPool jedisPool;

    @Autowired
    private FundMapper fundMapper;

    @Qualifier("alipay")
    @Autowired
    private FundService fundService;

    private final FundDto fundDto=new FundDto("1596903229@qq.com",20,1);

    @Test
    public void withdraw(){
        // 数据库修改数据
        fundMapper.withdraw(fundDto);

        // 数据库缓存修改数据
        Jedis jedis=jedisPool.getResource();

        String bean=jedis.get("info:" + fundDto.getEmail());

        User user=JSONUtil.toBean(bean, User.class);// 获取当前数据

        user.setBalance(user.getBalance()-fundDto.getAmount());// 修改数据

        jedis.set("info:" + fundDto.getEmail(),JSONUtil.toJsonStr(user));// 缓存写入数据

        jedis.close();
    }

}
