package org.eatgo.fund.service.impl;

import cn.hutool.json.JSONUtil;
import lombok.RequiredArgsConstructor;
import org.eatgo.common.domain.dto.FundDto;
import org.eatgo.common.domain.po.RechargeRecord;
import org.eatgo.common.domain.po.User;
import org.eatgo.fund.mapper.FundMapper;
import org.eatgo.fund.service.FundService;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@Service("wechat")
@RequiredArgsConstructor
public class FundServiceWeChatPayImpl implements FundService {

    private final JedisPool jedisPool;

    private final FundMapper fundMapper;

    @Override
    public void recharge(FundDto fundDto){
        // 从jedis连接池中获取一个可用的jedis连接
        Jedis jedis=jedisPool.getResource();

        // 更改数据库的数据
        fundMapper.recharge(fundDto);

        // 更新数据库缓存数据
        String bean=jedis.get("info:" + fundDto.getEmail());
        User u= JSONUtil.toBean(bean, User.class);// 获取当前数据
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

    @Override
    public void withdraw(FundDto fundDto) {
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
