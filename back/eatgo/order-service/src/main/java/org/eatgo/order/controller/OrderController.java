package org.eatgo.order.controller;

import lombok.RequiredArgsConstructor;
import org.eatgo.common.domain.dto.OrderDto;
import org.eatgo.common.domain.form.OrderTable;
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

    /**
    * admin
    * */
    @GetMapping("/order/table/list")
    public ResultVo<List<OrderTable>>orderTableData(){
        List<OrderTable> orders=orderService.orderTableList();
        return ResultVo.success("订单数据成功",orders);
    }

    @GetMapping("/order/search/list")
    public ResultVo<List<OrderTable>>SearchOrderTableList(
            @RequestParam(value = "status",required = false) Integer status){
        List<OrderTable>orders=orderService.SearchOrderTableList( status);

        return ResultVo.success("订单列表",orders);
    }

    @PutMapping("/order/update/{orderId}")
    public ResultVo<String>OrderReady(@PathVariable("orderId")Integer orderId){
        orderService.OrderReady(orderId);

        return ResultVo.success("消息提示","出餐成功");
    }
}
