package org.eatgo.user.service.impl;

import cn.hutool.core.util.ObjectUtil;
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
import redis.clients.jedis.JedisPool;

import java.util.HashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final EmailClient emailClient;

    private final UserMapper userMapper;

    private final JedisPool jedisPool;

    @Override
    public ResultVo<String> sendEmail(LoginDto loginDto) {//发送邮件
        return emailClient.sendEmail(loginDto);
    }

    @Override
    public ResultVo<String> register(LoginDto loginDto) {//注册

        Jedis jedis = jedisPool.getResource();

        String validCode=jedis.get("valid_code:" + loginDto.getEmail());

        if(!validCode.equals(loginDto.getValidCode())){
            throw new ValidCodeException("验证码非法");
        }else{
            userMapper.register(loginDto);
        }

        jedis.close();
        return ResultVo.success("注册成功",null);
    }

    @Override
    public ResultVo<String> login(LoginDto loginDto){//登录
        Jedis jedis = jedisPool.getResource();

        String code=jedis.get("valid_code:" + loginDto.getEmail());

        User u=userMapper.login(loginDto);

        System.out.println(u);

        if(loginDto.getValidCode().equals(code) && !JSONUtil.isNull(u) && !u.getIsEffective().equals(0)){//验证码合法
            //嵌合数据
            HashMap<String, Object> map=new HashMap<>();
            map.put("email",u.getEmail());
            map.put("password",u.getPassword());
            //生成令牌
            String token = JWTUtil.createToken(map, "1234".getBytes());
            //缓存token
            jedis.setex("token:"+u.getEmail(),60*60*24,token);
            //缓存用户信息
            jedis.setex("info:"+u.getEmail(),60*60*24,JSONUtil.toJsonStr(u));
            //200返回
            return ResultVo.success("登录成功",token);
        }else if (!loginDto.getValidCode().equals(code)){
            return ResultVo.error(10002,"验证码错误");
        } else if (JSONUtil.isNull(u)) {
            return ResultVo.error(10003,"用户名或密码错误");
        } else if (u.getIsEffective()==0){
            return ResultVo.error(10005,"用户已经冻结");
        }

        jedis.close();
        return ResultVo.error(10000,"系统错误");
    }

    @Override
    public ResultVo<String> resetPassword(LoginDto loginDto) {//重设密码
        Jedis jedis = jedisPool.getResource();

        //1.获取验证码
        String code=jedis.get("valid_code:" + loginDto.getEmail());

        if(loginDto.getValidCode().equals(code)){
            userMapper.resetPassword(loginDto);

            jedis.close();
            return ResultVo.success("success","重设密码成功");
        }else{

            jedis.close();
            return ResultVo.error(10002,"验证码错误");
        }

    }

    @Override
    public Boolean verifyToken(String token, String email) {

        Jedis jedis = jedisPool.getResource();

        boolean flag=true;

        String t=jedis.get("token:" + email);

        flag=ObjectUtil.equals(t, token);

        jedis.close();

        return flag;
    }

    @Override
    public User getUserByEmail(String email) {
        Jedis jedis = jedisPool.getResource();

        String s=jedis.get("info:" + email);

        jedis.close();

        return JSONUtil.toBean(s, User.class);
    }

    @Override
    public void EditUser(User user) {

        // 数据库更新数据
        userMapper.EditUser(user);

        // 数据库更新缓存
        Jedis jedis=jedisPool.getResource();

        jedis.set("info:"+user.getEmail(),JSONUtil.toJsonStr(user));

        jedis.close();
    }

    @Override
    public List<User> list() {
        return userMapper.list();
    }

    @Override
    public String AdminLoginByEmail(LoginDto loginDto) {

        Jedis jedis=jedisPool.getResource();

        String code=jedis.get("valid_code:" + loginDto.getEmail());

        if(loginDto.getValidCode().equals(code)){
            HashMap<String, Object> map=new HashMap<>();
            map.put("email",loginDto.getEmail());
            map.put("password",loginDto.getValidCode());
            // 生成令牌
            String token=JWTUtil.createToken(map, "1234".getBytes());
            // 服务器缓存Token
            jedis.setex("token:"+loginDto.getEmail(),60*60*24,token);
        }

        return null;
    }

    @Override
    public void updateUserEffective(User user) {
        System.out.println(user.getEmail());
        Jedis jedis=jedisPool.getResource();
        Integer effective=user.getIsEffective();

        if(ObjectUtil.equals(effective,1)){//冻结
            user.setIsEffective(0);
            // 数据库修改数据
            userMapper.updateUserEffective(user);

            //数据库缓存操作
            jedis.del("info:"+user.getEmail());
            jedis.del("token:"+user.getEmail());
            jedis.close();
        } else if (ObjectUtil.equals(effective,0)) {
            user.setIsEffective(1);
            userMapper.updateUserEffective(user);
        }
    }
}
