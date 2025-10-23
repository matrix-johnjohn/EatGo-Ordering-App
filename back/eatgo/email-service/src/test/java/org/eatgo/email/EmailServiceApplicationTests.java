package org.eatgo.email;

import org.eatgo.common.exception.user.EmailNotFoundException;
import org.eatgo.email.service.EmailService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

@SpringBootTest
class EmailServiceApplicationTests {

    @Autowired
    private EmailService emailService;

    @Test
    void contextLoads() {
    }

}
