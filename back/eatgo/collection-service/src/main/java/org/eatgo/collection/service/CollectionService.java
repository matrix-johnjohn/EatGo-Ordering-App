package org.eatgo.collection.service;

import org.eatgo.common.domain.po.Collection;
import org.eatgo.common.domain.po.Dish;
import org.eatgo.common.domain.query.CollectionQuery;

import java.util.List;

public interface CollectionService {

    public void collect(CollectionQuery collectionQuery);//收藏接口

    public void cancelCollect(CollectionQuery collectionQuery);//取消收藏接口

    public List<Collection> collectionList(Integer userId);//收藏列表接口

    public List<Dish> collectionDishList(Integer userId);//收藏菜品详情列表接口

}
