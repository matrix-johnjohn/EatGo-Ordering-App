package org.eatgo.order.client;

import org.eatgo.common.domain.po.Dish;
import org.eatgo.common.domain.vo.ResultVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient("menu-service")
public interface MenuClient {
    @GetMapping("/menu/dish/list")
    public ResultVo<List<Dish>> dishList();
}
