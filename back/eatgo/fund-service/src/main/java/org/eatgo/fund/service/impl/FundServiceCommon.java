package org.eatgo.fund.service.impl;

import cn.hutool.json.JSONUtil;
import lombok.RequiredArgsConstructor;
import org.eatgo.common.domain.dto.FundDto;
import org.eatgo.common.domain.po.User;
import org.eatgo.common.domain.query.DeductionQuery;
import org.eatgo.fund.mapper.FundMapper;
import org.eatgo.fund.service.FundService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@Service("common")
@RequiredArgsConstructor
public class FundServiceCommon implements FundService {


    private final JedisPool jedisPool;

    private final FundMapper fundMapper;

    @Override
    public void recharge(FundDto balanceDto) {

    }

    @Override
    public void withdraw(FundDto balanceDto) {

    }

    @Override
    public void Deduct(DeductionQuery deductionQuery) {
        // 数据库修改数据
        fundMapper.Deduction(deductionQuery);

        // 数据库缓存修改数据
        Jedis jedis=jedisPool.getResource();

        String json=jedis.get("info:"+deductionQuery.getEmail());

        User user= JSONUtil.toBean(json, User.class);

        user.setBalance(user.getBalance()-deductionQuery.getPrice());

        String jsonStr=JSONUtil.toJsonStr(user);

        jedis.set("info:" + deductionQuery.getEmail(),jsonStr);

        jedis.close();
    }
}
