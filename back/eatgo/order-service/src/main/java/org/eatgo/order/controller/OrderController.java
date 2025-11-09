package org.eatgo.order.controller;

import lombok.RequiredArgsConstructor;
import org.eatgo.common.domain.dto.OrderDto;
import org.eatgo.common.domain.vo.OrderVo;
import org.eatgo.common.domain.vo.ResultVo;
import org.eatgo.order.service.OrderService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/list/{userId}/{status}")
    public ResultVo<List<OrderVo>> getOrderById(@PathVariable("userId") Integer userId,@PathVariable("status") Integer status) {
        List<OrderVo> orderVos = orderService.orderList(userId,status);

        return ResultVo.success("",orderVos);
    }

    @PutMapping("/update/order/{orderId}/{status}")
    public ResultVo<String>UpdateOrder(@PathVariable("orderId") Integer orderId,@PathVariable("status") Integer status) {
        orderService.setOrderStatus(orderId,status);
        return ResultVo.success("订单更新","订单数据更新成功");
    }
}
