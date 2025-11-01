package org.eatgo.collection;

import cn.hutool.json.JSON;
import cn.hutool.json.JSONUtil;
import org.eatgo.collection.mapper.CollectionMapper;
import org.eatgo.collection.service.CollectionService;
import org.eatgo.common.domain.po.Collection;
import org.eatgo.common.domain.query.CollectionQuery;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import redis.clients.jedis.Jedis;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
public class CollectionServiceApplicationTest {

    @Autowired
    private CollectionMapper collectionMapper;

    @Autowired
    private Jedis jedis;

    @Autowired
    private CollectionService collectionService;

    @Test
    public void test1(){
        collectionService.collect(new CollectionQuery(15,1));
    }

    @Test
    public void test2(){
        collectionService.cancelCollect(new CollectionQuery(15,1));
    }

    @Test
    public void test3(){//redis添加用户收藏数据
        JSON json=JSONUtil.parse(new Collection(0, 15, 3, LocalDateTime.now(), LocalDateTime.now()));

        System.out.println(json.toString());

        jedis.rpush("collection:list:15",json.toString());
    }

    @Test
    public void test4(){//redis删除用户收藏数据

        int userId=15;

        int dishId=3;

        List<String> l=jedis.lrange("collection:list:15", 0, -1);

        for(String s:l){//遍历键为collection:list:15的列表

            Collection c=JSONUtil.toBean(s, Collection.class);//对象转换

            if(c.getUserId()==userId&&c.getDishId()==dishId){//找到元素

                jedis.lrem("collection:list:15",1,s);//删除元素

                return;
            }
        }
    }
}
