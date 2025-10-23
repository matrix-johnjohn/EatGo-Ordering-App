package org.eatgo.email;

import org.eatgo.common.exception.user.EmailNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

@SpringBootTest
class EmailServiceApplicationTests {

    @Autowired
    private JavaMailSender mailSender;

    @Test
    void contextLoads() {
        SimpleMailMessage mail=new SimpleMailMessage();

        mail.setSubject("邮箱标题");

        mail.setText("内容:验证码为RHSzTpKJ3EBZvqhF,请收好");//设置邮箱内容

        mail.setTo("jffdsgsd4ijmvoi");

        mail.setFrom("13929944553@163.com");

        try{
            mailSender.send(mail);
        }catch (Exception e){
            throw new EmailNotFoundException("邮箱输错了我不睡着啊");
        }
    }

}
