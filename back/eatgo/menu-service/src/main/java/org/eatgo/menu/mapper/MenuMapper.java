package org.eatgo.menu.mapper;

import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.eatgo.common.domain.po.Dish;
import org.eatgo.common.domain.po.DishCategorize;
import org.eatgo.common.domain.po.DishTag;
import org.eatgo.common.domain.query.CollectionQuery;
import org.eatgo.common.domain.query.DishQuery;

import java.util.List;


public interface MenuMapper {

    @Select("select * from dish_cate")
    public List<DishCategorize> cateList();//分类列表

    public List<DishTag>tagList(DishCategorize dishCategorize);//标签列表

    public List<Dish>dishList(DishQuery dishQuery);//菜品列表

    @Update("update dish set collection_count=collection_count+1 where id=#{dishId}")
    public void plusCount(CollectionQuery collectionQuery);

    @Update("update dish set collection_count=collection_count-1 where id=#{dishId}")
    public void minusCount(CollectionQuery collectionQuery);

    public List<Dish>dishesByIds(List<Integer>list);
}
