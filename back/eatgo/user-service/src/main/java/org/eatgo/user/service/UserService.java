package org.eatgo.user.service;

import org.eatgo.common.domain.dto.LoginDto;
import org.eatgo.common.domain.po.User;
import org.eatgo.common.domain.vo.ResultVo;

import java.util.List;

public interface UserService {

    public ResultVo<String> sendEmail(LoginDto loginDto);

    public ResultVo<String> register(LoginDto loginDto);

    public ResultVo<String> login(LoginDto loginDto);

    public ResultVo<String> resetPassword(LoginDto loginDto);

    public Boolean verifyToken(String token,String email);

    public User getUserByEmail(String email);//根据email获取用户信息

    public void EditUser(User user);

    public List<User> list();

    // 后台管理
    public String AdminLoginByEmail(LoginDto loginDto);

    public void updateUserEffective(User user);
}
