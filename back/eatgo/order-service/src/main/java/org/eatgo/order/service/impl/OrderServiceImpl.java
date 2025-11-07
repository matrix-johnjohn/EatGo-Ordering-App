package org.eatgo.order.service.impl;

import lombok.RequiredArgsConstructor;
import org.eatgo.common.domain.dto.OrderDto;
import org.eatgo.order.mapper.OrderMapper;
import org.eatgo.order.service.OrderService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderMapper orderMapper;

    @Override
    public void insertOrder(OrderDto orderDto) {
        orderMapper.insertOrder(orderDto);
    }
}
