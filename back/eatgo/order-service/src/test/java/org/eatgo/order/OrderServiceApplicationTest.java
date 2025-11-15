package org.eatgo.order;

import org.eatgo.common.domain.dto.OrderDto;
import org.eatgo.common.domain.po.Dish;
import org.eatgo.common.domain.vo.OrderVo;
import org.eatgo.order.client.MenuClient;
import org.eatgo.order.mapper.OrderMapper;
import org.eatgo.order.service.OrderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class OrderServiceApplicationTest {

    @Autowired
    private MenuClient menuClient;

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderMapper orderMapper;

    @Test
    public void test1(){
        orderService.insertOrder(new OrderDto(15,3,51.2,3,153.6));
    }

    @Test
    public void test3(){
        // 更改订单状态
        orderMapper.setOrderStatus(17,1);
    }

    @Test
    public void test2(){
        List<OrderVo> orderVos = orderService.orderList(-1, -1);

        orderVos.forEach(System.out::println);
    }
}
