package org.eatgo.order.controller;

import lombok.RequiredArgsConstructor;
import org.eatgo.common.domain.dto.OrderDto;
import org.eatgo.common.domain.vo.ResultVo;
import org.eatgo.order.service.OrderService;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PutMapping("/insert/order")
    public ResultVo<String> insertOrder(@RequestBody OrderDto orderDto) {
        orderService.insertOrder(orderDto);

        return ResultVo.success("下单成功",null);
    }
}
