package org.eatgo.order.service;

import org.eatgo.common.domain.dto.OrderDto;

public interface OrderService {

    public void insertOrder(OrderDto orderDto);
}
