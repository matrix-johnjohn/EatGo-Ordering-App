package org.eatgo.user.service.impl;

import lombok.RequiredArgsConstructor;
import org.eatgo.common.domain.dto.LoginDto;
import org.eatgo.common.domain.vo.ResultVo;
import org.eatgo.user.client.EmailClient;
import org.eatgo.user.service.UserService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final EmailClient emailClient;

    @Override
    public ResultVo<String> sendEmail(LoginDto loginDto) {
        return emailClient.sendEmail(loginDto);
    }
}
