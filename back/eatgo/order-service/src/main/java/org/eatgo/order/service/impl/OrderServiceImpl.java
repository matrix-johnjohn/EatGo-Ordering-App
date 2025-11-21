package org.eatgo.order.service.impl;

import cn.hutool.json.JSONUtil;
import lombok.RequiredArgsConstructor;
import org.eatgo.common.domain.dto.OrderDto;
import org.eatgo.common.domain.form.OrderTable;
import org.eatgo.common.domain.po.Dish;
import org.eatgo.common.domain.po.User;
import org.eatgo.common.domain.vo.DishVo;
import org.eatgo.common.domain.vo.OrderVo;
import org.eatgo.order.client.MenuClient;
import org.eatgo.order.mapper.OrderMapper;
import org.eatgo.order.service.OrderService;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderMapper orderMapper;

    private final MenuClient menuClient;

    private final JedisPool jedisPool;

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

    @Override
    public List<OrderTable> orderTableList() {
        List<OrderTable>orders=orderMapper.OrderTableList();

        Jedis jedis=jedisPool.getResource();

        for(OrderTable order:orders){
            String userKey="user:info:"+order.getUserId();// 用户信息key

            String dishKey="dish:dish:"+order.getDishId();// 菜品信息key

            //获取用户缓存数据
            String userJSON=jedis.get(userKey);
            User userData= JSONUtil.toBean(userJSON, User.class);

            // 获取菜品缓存数据
            String dishJSON=jedis.get(dishKey);
            DishVo dishData=JSONUtil.toBean(dishJSON, DishVo.class);

            order.setUsername(userData.getUsername());
            order.setDishName(dishData.getTitle());
            order.setDishImg(dishData.getImage());
            order.setDishDesc(dishData.getDescription());
        }

        jedis.close();
        return orders;
    }

    @Override
    public List<OrderTable> SearchOrderTableList( Integer status) {

        List<OrderTable> orders=orderMapper.SearchOrderTable( status);

        Jedis jedis=jedisPool.getResource();

        for(OrderTable order:orders){
            String userKey="user:info:"+order.getUserId();// 用户信息key

            String dishKey="dish:dish:"+order.getDishId();// 菜品信息key

            //获取用户缓存数据
            String userJSON=jedis.get(userKey);
            User userData= JSONUtil.toBean(userJSON, User.class);

            // 获取菜品缓存数据
            String dishJSON=jedis.get(dishKey);
            DishVo dishData=JSONUtil.toBean(dishJSON, DishVo.class);

            order.setUsername(userData.getUsername());
            order.setDishName(dishData.getTitle());
            order.setDishImg(dishData.getImage());
            order.setDishDesc(dishData.getDescription());
        }

        jedis.close();

        return orders;
    }

    @Override
    public void OrderReady(Integer orderId) {
        orderMapper.OrderReady(orderId);

        orderMapper.updateTime(orderId);
    }
}
