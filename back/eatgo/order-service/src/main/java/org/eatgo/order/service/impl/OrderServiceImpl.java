package org.eatgo.order.service.impl;

import lombok.RequiredArgsConstructor;
import org.eatgo.common.domain.dto.OrderDto;
import org.eatgo.common.domain.po.Dish;
import org.eatgo.common.domain.vo.OrderVo;
import org.eatgo.order.client.MenuClient;
import org.eatgo.order.mapper.OrderMapper;
import org.eatgo.order.service.OrderService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderMapper orderMapper;

    private final MenuClient menuClient;

    @Override
    public void insertOrder(OrderDto orderDto) {
        orderMapper.insertOrder(orderDto);
    }

    @Override
    public List<OrderVo> orderList(Integer userId,Integer status) {
        List<OrderVo>list=orderMapper.OrderList(userId,status);

        List<Dish>dishList=menuClient.dishList().getData();

        for (Dish dish:dishList) {
            for (OrderVo order:list) {
                if (dish.getId().equals(order.getDishId())) {
                    order.setDishDesc(dish.getDescription());
                    order.setDishName(dish.getTitle());
                    order.setDishImg(dish.getImage());
                }
            }
        }

        return list;

    }

    @Override
    public void setOrderStatus(Integer orderId, Integer status) {
        orderMapper.setOrderStatus(orderId,status);
    }
}
