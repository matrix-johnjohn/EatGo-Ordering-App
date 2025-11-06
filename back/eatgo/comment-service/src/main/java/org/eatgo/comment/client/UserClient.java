package org.eatgo.comment.client;

import org.eatgo.common.domain.po.User;
import org.eatgo.common.domain.vo.ResultVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.List;

@FeignClient("user-service")
public interface UserClient {

    @GetMapping("/user/user/list")
    public ResultVo<List<User>> userList();
}
