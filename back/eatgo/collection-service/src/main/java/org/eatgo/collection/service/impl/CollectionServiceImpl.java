package org.eatgo.collection.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSON;
import cn.hutool.json.JSONUtil;
import lombok.RequiredArgsConstructor;
import org.eatgo.collection.client.MenuClient;
import org.eatgo.collection.mapper.CollectionMapper;
import org.eatgo.collection.service.CollectionService;
import org.eatgo.common.domain.po.Collection;
import org.eatgo.common.domain.po.Dish;
import org.eatgo.common.domain.query.CollectionQuery;
import org.eatgo.common.domain.vo.ResultVo;
import org.eatgo.common.exception.collection.RepeatedClickEventException;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CollectionServiceImpl implements CollectionService {

    private final CollectionMapper collectionMapper;

    private final MenuClient menuClient;

    private final JedisPool jedisPool;

    @Override
    public void collect(CollectionQuery collectionQuery) {//收藏数据
        Jedis jedis = jedisPool.getResource();

        Collection collectionRecord=collectionMapper.selectCollectionItem(collectionQuery);//查找是否有这条数据

        boolean isEmptyObject=ObjectUtil.isEmpty(collectionRecord);

        if(isEmptyObject){//记录为空,添加数据
            collectionMapper.InsertCollection(collectionQuery);

            //增加dish列表的count
            menuClient.plus(collectionQuery);

            //将数据添加至服务器缓存

            //封装缓存数据
            Collection collection=new Collection(0, collectionQuery.getUserId(),
                    collectionQuery.getDishId(), LocalDateTime.now(), LocalDateTime.now());

            //将需要添加的数据转换为json字符串
            JSON json=JSONUtil.parse(collection);

            //添加数据
            jedis.rpush(String.format("collection:list:%s",collection.getUserId().toString()),json.toString());
        }else{ // 线程重叠处理
            throw new RepeatedClickEventException("请勿重复点击添加");
        }

        jedis.close();
    }

    @Override
    public void cancelCollect(CollectionQuery collectionQuery) {//取消收藏
        Jedis jedis = jedisPool.getResource();

        Collection collectionRecord=collectionMapper.selectCollectionItem(collectionQuery);//查找是否有这条数据

        boolean isEmptyObject=ObjectUtil.isEmpty(collectionRecord);

        //数据非空,删除数据
        if(!isEmptyObject){
            collectionMapper.removeCollectionItem(collectionQuery);

            //减少dish列表的count
            menuClient.minus(collectionQuery);

            //缓存数据
            List<String>list=jedis.lrange(
                    String.format("collection:list:%s",collectionQuery.getUserId().toString()),
                    0,
                    -1);

            for (String s:list) {
                Collection c=JSONUtil.toBean(s, Collection.class);//对象转换

                if(Objects.equals(c.getUserId(), collectionQuery.getUserId()) && Objects.equals(c.getDishId(), collectionQuery.getDishId())){//找到元素

                    jedis.lrem(
                            String.format("collection:list:%s",collectionQuery.getUserId().toString()),
                            1,
                            s);//删除元素

                    return;
                }
            }
        }else{ // 线程重叠处理
            throw new RepeatedClickEventException("请勿重复点击删除");
        }
        jedis.close();
    }

    @Override
    public List<Collection> collectionList(Integer userId) {
        Jedis jedis = jedisPool.getResource();

        List<String>list=jedis.lrange("collection:list:" + userId, 0, -1);

        List<Collection>collectionList=new ArrayList<Collection>();

        for(String item:list){
            Collection c=JSONUtil.toBean(item, Collection.class);

            collectionList.add(c);
        }

        jedis.close();

        return collectionList;
    }

    @Override
    public List<Dish> collectionDishList(Integer userId) {//收藏菜品列表逻辑处理

        List<Integer>ids=getUserCollectionDishIdByUserId(15);

        ResultVo<List<Dish>>res=menuClient.dishesList(ids);

        return res.getData();
    }

    public List<Integer> getUserCollectionDishIdByUserId(Integer userId){

        Jedis jedis = jedisPool.getResource();

        List<Integer>result=new ArrayList<>();

        List<String>collectionDishList=jedis.lrange("collection:list:" + userId//缓存数据的key
                , 0, -1);

        for (String s:collectionDishList) {
            Collection c=JSONUtil.toBean(s, Collection.class);
            Integer dishId=c.getDishId();
            result.add(dishId);
        }

        jedis.close();
        return result;
    }
}
