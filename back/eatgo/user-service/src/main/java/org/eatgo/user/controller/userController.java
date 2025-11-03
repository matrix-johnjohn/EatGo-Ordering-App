package org.eatgo.user.controller;

import lombok.RequiredArgsConstructor;
import org.eatgo.common.domain.dto.LoginDto;
import org.eatgo.common.domain.po.User;
import org.eatgo.common.domain.vo.ResultVo;
import org.eatgo.user.service.UserService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class userController {

    private final UserService userService;

    @PostMapping("/send")
    public ResultVo<String> sendValidCode(@RequestBody LoginDto loginDto){//发送邮件
        return userService.sendEmail(loginDto);
    }

    @PutMapping("/register")
    public ResultVo<String>register(@RequestBody LoginDto loginDto){//注册
        return userService.register(loginDto);
    }

    @PostMapping("/login")
    public ResultVo<String>login(@RequestBody LoginDto loginDto){//登录
        return userService.login(loginDto);
    }

    @PostMapping("/reset/passwd")
    public ResultVo<String>resetPassword(@RequestBody LoginDto loginDto){
        return userService.resetPassword(loginDto);
    }

    //验证token是否有效
    @GetMapping("/verification/token/{token}/{email}")
    public ResultVo<Boolean> verificationToken(@PathVariable("token") String token,@PathVariable("email") String email){
        Boolean f=userService.verifyToken(token, email);

        return f?ResultVo.success("已经登录", true):ResultVo.error(500,"当前用户非法");
    }

    @GetMapping("/get/user/{email}")
    public ResultVo<User> getUser(@PathVariable("email")String email){
        User u=userService.getUserByEmail(email);

        return ResultVo.success("",u);
    }

    //切换头像
    @PostMapping("/reset/avatar")
    public ResultVo<String>setAvatar(@RequestParam("avatar")MultipartFile avatar){
        System.out.println(avatar.getOriginalFilename());

        return ResultVo.success("文件上传成功",null);
    }

}
