package org.eatgo.user.handler;

import org.eatgo.common.domain.vo.ResultVo;
import org.eatgo.common.exception.user.EmailNotFoundException;
import org.eatgo.common.exception.user.ValidCodeException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class UserExceptionHandler {

    @ExceptionHandler(value = ValidCodeException.class)
    @ResponseBody
    public ResultVo<String> exceptionHandler(final Exception e) {
        return ResultVo.error(10002,e.getMessage());
    }
}
