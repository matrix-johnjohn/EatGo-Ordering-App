package org.eatgo.order.mapper;

import org.apache.ibatis.annotations.*;
import org.eatgo.common.domain.dto.OrderDto;
import org.eatgo.common.domain.vo.OrderVo;

import java.util.List;

@Mapper
public interface OrderMapper {

    @Insert("insert into dish_order (user_id,dish_id,price,count,total_price) values (#{userId},#{dishId},#{price},#{count},#{totalPrice})")
    public void insertOrder(OrderDto order);

    public List<OrderVo> OrderList(@Param("userId") Integer userId,@Param("status") Integer status);

    public void setOrderStatus(@Param("orderId") Integer orderId, @Param("status")Integer status);
}