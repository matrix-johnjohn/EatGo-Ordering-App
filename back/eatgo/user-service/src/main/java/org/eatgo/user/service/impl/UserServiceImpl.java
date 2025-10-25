package org.eatgo.user.service.impl;

import cn.hutool.json.JSONUtil;
import cn.hutool.jwt.JWTUtil;
import lombok.RequiredArgsConstructor;
import org.eatgo.common.domain.dto.LoginDto;
import org.eatgo.common.domain.po.User;
import org.eatgo.common.domain.vo.ResultVo;
import org.eatgo.common.exception.user.ValidCodeException;
import org.eatgo.user.client.EmailClient;
import org.eatgo.user.mapper.UserMapper;
import org.eatgo.user.service.UserService;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import java.util.HashMap;

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

    @Override
    public ResultVo<String> login(LoginDto loginDto) {

        String code=jedis.get("valid_code:" + loginDto.getEmail());

        User u=userMapper.login(loginDto);

        if(code.equals(loginDto.getValidCode()) && !JSONUtil.isNull(u)){//验证码合法
            //嵌合数据
            HashMap<String, Object> map=new HashMap<>();
            map.put("email",u.getEmail());
            map.put("password",u.getPassword());
            //生成令牌
            String token = JWTUtil.createToken(map, "1234".getBytes());
            //缓存token
            jedis.setex("token:"+u.getEmail(),60*60*24,token);
            //缓存用户信息
            jedis.setex("info"+u.getEmail(),60*60*24,JSONUtil.toJsonStr(u));
            //200返回
            return ResultVo.success("登录成功",token);
        }else if (!code.equals(loginDto.getValidCode())){
            return ResultVo.error(10002,"验证码错误");
        } else if (JSONUtil.isNull(u)) {
            return ResultVo.error(10003,"用户名或密码错误");
        }
        return ResultVo.error(10000,"系统错误");
    }
}
