package org.eatgo.user.service;

import org.eatgo.common.domain.dto.LoginDto;
import org.eatgo.common.domain.vo.ResultVo;

public interface UserService {

    public ResultVo<String> sendEmail(LoginDto loginDto);

    public ResultVo<String> register(LoginDto loginDto);

    public ResultVo<String> login(LoginDto loginDto);
}
