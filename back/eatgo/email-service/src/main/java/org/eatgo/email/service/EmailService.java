package org.eatgo.email.service;

import org.eatgo.common.domain.dto.LoginDto;

public interface EmailService {
    public void sendEmail(LoginDto loginDto);
}
