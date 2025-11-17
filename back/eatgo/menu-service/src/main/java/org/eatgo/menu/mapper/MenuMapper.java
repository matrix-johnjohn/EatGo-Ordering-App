package org.eatgo.menu.mapper;

import org.apache.ibatis.annotations.*;
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

    public List<Dish>recommendList();

    @Select("select * from dish where id=#{dishId}")
    public Dish getDishById(Integer dishId);


    // 后台管理
    @Delete("delete from dish_cate where id=#{id}")
    public void deleteDishCateById(DishCategorize dishCategorize);//删除分类

    // 批量删除分类
    public void deleteDishCateByIds(@Param("ids") List<Integer>ids);

    // 添加分类
    @Insert("insert into dish_cate (name,icon,banner) values (#{name},#{icon},#{banner})")
    public void addCate(DishCategorize dishCategorize);

    @Update("update dish_cate set banner=#{banner} where id=#{id}")
    public void updateCateBanner(DishCategorize dishCategorize);

    public void updateCate(DishCategorize dishCategorize);

    @Select("select * from `dish_cate` where `name` like CONCAT('%',#{subName},'%')")
    public List<DishCategorize>searchCateList(@Param("subName") String subName);
}
