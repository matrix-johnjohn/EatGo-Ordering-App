package org.eatgo.collection;

import cn.hutool.json.JSONUtil;
import org.eatgo.collection.client.MenuClient;
import org.eatgo.common.domain.po.Collection;
import org.eatgo.common.domain.po.Dish;
import org.eatgo.common.domain.vo.ResultVo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class CollectionServiceApplicationTest {

    @Autowired
    private MenuClient menuClient;

    @Autowired
    private JedisPool jedisPool;

    @Test
    public void test1(){
        List<Integer>ids=getUserCollectionDishIdByUserId(15);

        ResultVo<List<Dish>>list=menuClient.dishesList(ids);

        list.getData().forEach(System.out::println);
    }

    public List<Integer> getUserCollectionDishIdByUserId(Integer userId){

        List<Integer>result=new ArrayList<>();

        Jedis jedis = jedisPool.getResource();

        List<String>collectionDishList=jedis.lrange("collection:list:" + userId//缓存数据的key
                , 0, -1);

        for (String s:collectionDishList) {
            Collection c=JSONUtil.toBean(s, Collection.class);
            Integer dishId=c.getDishId();
            result.add(dishId);
        }

        return result;
    }
}
