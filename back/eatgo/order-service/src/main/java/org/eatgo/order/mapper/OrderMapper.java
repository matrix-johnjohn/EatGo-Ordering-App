package org.eatgo.order.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.eatgo.common.domain.dto.OrderDto;

@Mapper
public interface OrderMapper {

    @Insert("insert into dish_order (user_id,dish_id,price,count,total_price) values (#{userId},#{dishId},#{price},#{count},#{totalPrice})")
    public void insertOrder(OrderDto order);
}