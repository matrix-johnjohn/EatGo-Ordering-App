package org.eatgo.collection.handler;

import org.eatgo.common.domain.vo.ResultVo;
import org.eatgo.common.exception.collection.RepeatedClickEventException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class CollectionExceptionHandler {

    @ExceptionHandler(value = RepeatedClickEventException.class)
    @ResponseBody
    public ResultVo<String> exceptionHandler(Exception e) {
        return ResultVo.error(10004, e.getMessage());
    }
}
