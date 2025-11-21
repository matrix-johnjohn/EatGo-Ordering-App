package org.eatgo.order.service;

import org.apache.ibatis.annotations.Param;
import org.eatgo.common.domain.dto.OrderDto;
import org.eatgo.common.domain.form.OrderTable;
import org.eatgo.common.domain.vo.OrderVo;

import java.util.List;

public interface OrderService {

    public void insertOrder(OrderDto orderDto);

    public List<OrderVo> orderList(Integer userId,Integer status);

    public void setOrderStatus(Integer orderId, Integer status);


    /*
    * Admin
    * */
    public List<OrderTable> orderTableList();

    public List<OrderTable> SearchOrderTableList(Integer status);

    public void OrderReady(Integer orderId);
}
