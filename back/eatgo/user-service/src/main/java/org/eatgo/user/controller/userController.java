package org.eatgo.user.controller;

import lombok.RequiredArgsConstructor;
import org.eatgo.common.domain.dto.LoginDto;
import org.eatgo.common.domain.vo.ResultVo;
import org.eatgo.user.service.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class userController {

    private final UserService userService;

    @PostMapping("/send")
    public ResultVo<String> sendValidCode(@RequestBody LoginDto loginDto){

        return userService.sendEmail(loginDto);
    }

    @PutMapping("/register")
    public ResultVo<String>register(@RequestBody LoginDto loginDto){
        return userService.register(loginDto);
    }

    @GetMapping("/login")
    public ResultVo<String>login(@RequestBody LoginDto loginDto){
        return null;
    }
}
