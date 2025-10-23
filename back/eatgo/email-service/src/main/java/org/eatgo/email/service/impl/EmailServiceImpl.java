package org.eatgo.email.service.impl;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.eatgo.common.domain.dto.LoginDto;
import org.eatgo.common.exception.user.EmailNotFoundException;
import org.eatgo.email.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import java.util.UUID;

@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private Jedis jedis;

    @Override
    public void sendEmail(LoginDto loginDto) {
        SimpleMailMessage mail=new SimpleMailMessage();

        mail.setSubject("邮箱标题");

        String validCode = GenerateValidCode();

        jedis.setex("valid_code:"+loginDto.getEmail(), 5*60,validCode);

        mail.setText("内容:验证码为"+validCode+",请收好");//设置邮箱内容

        mail.setTo(loginDto.getEmail());//1596903229@qq.com

        mail.setFrom("13929944553@163.com");

        try{
            mailSender.send(mail);
        }catch (Exception e){
            throw new EmailNotFoundException("邮箱非法,请重新输入");
        }
    }

    //生成验证码
    public String GenerateValidCode(){
        UUID uuid = UUID.randomUUID();

        return uuid.toString().toLowerCase().substring(0, 6);
    }
}
