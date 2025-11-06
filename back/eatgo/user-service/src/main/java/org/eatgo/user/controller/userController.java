package org.eatgo.user.controller;

import lombok.RequiredArgsConstructor;
import org.eatgo.common.domain.dto.LoginDto;
import org.eatgo.common.domain.po.User;
import org.eatgo.common.domain.vo.ResultVo;
import org.eatgo.user.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class userController {

    private final UserService userService;

    // 发送邮箱验证码
    @PostMapping("/send")
    public ResultVo<String> sendValidCode(@RequestBody LoginDto loginDto){//发送邮件
        return userService.sendEmail(loginDto);
    }

    // 注册
    @PutMapping("/register")
    public ResultVo<String>register(@RequestBody LoginDto loginDto){//注册
        return userService.register(loginDto);
    }

    // 登录
    @PostMapping("/login")
    public ResultVo<String>login(@RequestBody LoginDto loginDto){//登录
        return userService.login(loginDto);
    }

    // 重设密码
    @PostMapping("/reset/passwd")
    public ResultVo<String>resetPassword(@RequestBody LoginDto loginDto){
        return userService.resetPassword(loginDto);
    }

    // 验证当前token是否有效
    @GetMapping("/verification/token/{token}/{email}")
    public ResultVo<Boolean> verificationToken(@PathVariable("token") String token,@PathVariable("email") String email){
        Boolean f=userService.verifyToken(token, email);

        return f?ResultVo.success("已经登录", true):ResultVo.error(500,"当前用户非法");
    }

    // 获取当前用户
    @GetMapping("/get/user/{email}")
    public ResultVo<User> getUser(@PathVariable("email")String email){
        User u=userService.getUserByEmail(email);

        return ResultVo.success("",u);
    }

    // 更新用户信息
    @PutMapping("/edit/user")
    public ResultVo<String> editUser(@RequestBody User user){

        userService.EditUser(user);

        return ResultVo.success("",null);
    }

    @GetMapping("/user/list")
    public ResultVo<List<User>>userList(){
        List<User> list=userService.list();

        return ResultVo.success("获取列表",list);
    }
}
