package org.eatgo.order;

import org.eatgo.common.domain.dto.OrderDto;
import org.eatgo.order.service.OrderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class OrderServiceApplicationTest {

    @Autowired
    OrderService orderService;

    @Test
    public void test(){
        orderService.insertOrder(new OrderDto(15,3,51.2,3,153.6));
    }
}
