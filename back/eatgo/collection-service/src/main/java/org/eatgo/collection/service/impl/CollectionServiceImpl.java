package org.eatgo.collection.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSON;
import cn.hutool.json.JSONUtil;
import lombok.RequiredArgsConstructor;
import org.eatgo.collection.client.MenuClient;
import org.eatgo.collection.mapper.CollectionMapper;
import org.eatgo.collection.service.CollectionService;
import org.eatgo.common.domain.po.Collection;
import org.eatgo.common.domain.query.CollectionQuery;
import org.eatgo.common.exception.collection.RepeatedClickEventException;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CollectionServiceImpl implements CollectionService {

    private final CollectionMapper collectionMapper;

    private final MenuClient menuClient;

    private final Jedis jedis;

    @Override
    public void collect(CollectionQuery collectionQuery) {//收藏数据
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
    }

    @Override
    public void cancelCollect(CollectionQuery collectionQuery) {//取消收藏
        Collection collectionRecord=collectionMapper.selectCollectionItem(collectionQuery);//查找是否有这条数据

        boolean isEmptyObject=ObjectUtil.isEmpty(collectionRecord);

        //数据非空,删除数据
        if(!isEmptyObject){
            collectionMapper.removeCollectionItem(collectionQuery);

            //减少dish列表的count
            menuClient.minus(collectionQuery);

            //Todo:删除服务器缓存

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
    }

    @Override
    public List<Collection> collectionList(Integer userId) {
        List<String>list=jedis.lrange("collection:list:" + userId, 0, -1);

        List<Collection>collectionList=new ArrayList<Collection>();

        for(String item:list){
            Collection c=JSONUtil.toBean(item, Collection.class);

            collectionList.add(c);
        }

        return collectionList;
    }
}
