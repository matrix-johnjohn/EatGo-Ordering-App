package org.eatgo.order.mapper;

import org.apache.ibatis.annotations.*;
import org.eatgo.common.domain.dto.OrderDto;
import org.eatgo.common.domain.form.OrderTable;
import org.eatgo.common.domain.vo.OrderVo;

import java.util.List;

@Mapper
public interface OrderMapper {

    @Insert("insert into dish_order (user_id,dish_id,price,count,total_price) values (#{userId},#{dishId},#{price},#{count},#{totalPrice})")
    public void insertOrder(OrderDto order);

    public List<OrderVo> OrderList(@Param("userId") Integer userId,@Param("status") Integer status);

    public void setOrderStatus(@Param("orderId") Integer orderId, @Param("status")Integer status);

    // 后台管理
    @Select("select * from `dish_order`")
    public List<OrderTable> OrderTableList();//订单列表

    public List<OrderTable> SearchOrderTable(@Param("status") Integer status);//搜索列表

    @Update("update `dish_order` set status=1 where id=#{orderId}")
    public void OrderReady(@Param("orderId") Integer orderId);// 出餐

    @Update("update `dish_order` set update_time=now() where id=#{orderId}")
    public void updateTime(@Param("orderId") Integer orderId);// 设置出餐时间
}