package org.eatgo.email.handler;

import org.eatgo.common.domain.vo.ResultVo;
import org.eatgo.common.exception.user.EmailNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class LoginExceptionHandler {

    @ExceptionHandler(value = EmailNotFoundException.class)
    @ResponseBody
    public ResultVo<String> exceptionHandler(final Exception e) {
        return ResultVo.error(10001,e.getMessage());
    }
}
