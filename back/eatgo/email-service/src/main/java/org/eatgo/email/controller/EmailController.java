package org.eatgo.email.controller;

import org.eatgo.common.domain.dto.LoginDto;
import org.eatgo.common.domain.vo.ResultVo;
import org.eatgo.email.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/mail")
public class EmailController {

    @Autowired
    private EmailService emailService;

    @PostMapping("/send")
    public ResultVo<String>sendEmail(@RequestBody LoginDto loginDto) {
        emailService.sendEmail(loginDto);

        return ResultVo.success("发送成功",null);
    }
}
