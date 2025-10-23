package org.eatgo.user.service.impl;

import lombok.RequiredArgsConstructor;
import org.eatgo.common.domain.dto.LoginDto;
import org.eatgo.common.domain.vo.ResultVo;
import org.eatgo.common.exception.user.ValidCodeException;
import org.eatgo.user.client.EmailClient;
import org.eatgo.user.mapper.UserMapper;
import org.eatgo.user.service.UserService;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final EmailClient emailClient;

    private final UserMapper userMapper;

    private final Jedis jedis;

    @Override
    public ResultVo<String> sendEmail(LoginDto loginDto) {
        return emailClient.sendEmail(loginDto);
    }

    @Override
    public ResultVo<String> register(LoginDto loginDto) {

        String validCode=jedis.get("valid_code:" + loginDto.getEmail());

        if(!validCode.equals(loginDto.getValidCode())){
            throw new ValidCodeException("验证码非法");
        }else{
            userMapper.register(loginDto);
        }

        return ResultVo.success("注册成功",null);
    }
}
