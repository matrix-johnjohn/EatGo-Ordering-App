package org.eatgo.order;

import cn.hutool.json.JSONUtil;
import org.eatgo.common.domain.dto.OrderDto;
import org.eatgo.common.domain.form.OrderTable;
import org.eatgo.common.domain.po.Dish;
import org.eatgo.common.domain.po.User;
import org.eatgo.common.domain.vo.DishVo;
import org.eatgo.common.domain.vo.OrderVo;
import org.eatgo.order.client.MenuClient;
import org.eatgo.order.mapper.OrderMapper;
import org.eatgo.order.service.OrderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

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

    @Autowired
    private JedisPool jedisPool;


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

    @Test
    public void test4(){
        List<OrderTable>orders=orderMapper.OrderTableList();

        Jedis jedis=jedisPool.getResource();

        for(OrderTable order:orders){
            String userKey="user:info:"+order.getUserId();// 用户信息key

            String dishKey="dish:dish:"+order.getDishId();// 菜品信息key

            //获取用户缓存数据
            String userJSON=jedis.get(userKey);
            User userData=JSONUtil.toBean(userJSON, User.class);

            // 获取菜品缓存数据
            String dishJSON=jedis.get(dishKey);
            DishVo dishData=JSONUtil.toBean(dishJSON, DishVo.class);

            order.setUsername(userData.getUsername());
            order.setDishName(dishData.getTitle());
            order.setDishImg(dishData.getImage());
            order.setDishDesc(dishData.getDescription());
        }

        jedis.close();
    }

    @Test
    public void test5(){
    }
}
