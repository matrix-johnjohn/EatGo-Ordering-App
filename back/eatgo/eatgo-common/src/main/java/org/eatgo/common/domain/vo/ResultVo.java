package org.eatgo.common.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResultVo<T> {
    private Integer code;
    private String message;
    private T data;

    public static <T> ResultVo<T> success(String message,T data) {
        return new ResultVo<T>(200, message, data);
    }

    public static <T> ResultVo<T> error(Integer code,String message) {
        return new ResultVo<T>(code, message, null);
    }
}
