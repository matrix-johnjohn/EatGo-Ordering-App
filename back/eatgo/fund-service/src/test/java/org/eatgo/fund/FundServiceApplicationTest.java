package org.eatgo.fund;

import cn.hutool.json.JSONUtil;
import org.eatgo.common.domain.dto.FundDto;
import org.eatgo.common.domain.po.RechargeRecord;
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
    public void weChatPayMapper(){
        // 从jedis连接池中获取一个可用的jedis连接
        Jedis jedis=jedisPool.getResource();

        // 更改数据库的数据
        fundMapper.recharge(fundDto);

        // 更新数据库缓存数据
        String bean=jedis.get("info:" + fundDto.getEmail());
        User u=JSONUtil.toBean(bean, User.class);// 获取当前数据
        u.setBalance(u.getBalance()+fundDto.getAmount());// 设置数据
        jedis.set("info:" + fundDto.getEmail(),JSONUtil.toJsonStr(u));// 重新设置数据

        // 往数据库添加充值记录
        RechargeRecord rechargeRecord=new RechargeRecord();//组装需要添加的数据
        rechargeRecord.setUserId(u.getId());
        rechargeRecord.setMethod(fundDto.getMethod());
        rechargeRecord.setAmount(fundDto.getAmount());
        fundMapper.insertRechargeRecord(rechargeRecord);

        // Todo: 接入微信支付沙箱
        System.out.println("接入微信支付沙箱");

        // 关闭连接
        jedis.close();
    }

    @Test
    public void weChatPayService(){
        fundService.recharge(fundDto);
    }

}
