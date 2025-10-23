package org.eatgo.user.client;

import org.eatgo.common.domain.dto.LoginDto;
import org.eatgo.common.domain.vo.ResultVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient("email-service")
public interface EmailClient {
    @PostMapping("/mail/send")
    public ResultVo<String> sendEmail(@RequestBody LoginDto loginDto);
}
