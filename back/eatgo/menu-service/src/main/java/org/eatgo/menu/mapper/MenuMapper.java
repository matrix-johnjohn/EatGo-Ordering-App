package org.eatgo.menu.mapper;

import org.apache.ibatis.annotations.*;
import org.eatgo.common.domain.po.Dish;
import org.eatgo.common.domain.po.DishCategorize;
import org.eatgo.common.domain.po.DishTag;
import org.eatgo.common.domain.query.CollectionQuery;
import org.eatgo.common.domain.query.DishQuery;
import org.eatgo.common.domain.query.UpdateDishTagQuery;
import org.eatgo.common.domain.vo.DishTagVo;

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

    // 更新分类海报
    @Update("update dish_cate set banner=#{banner} where id=#{id}")
    public void updateCateBanner(DishCategorize dishCategorize);

    // 更新分类
    public void updateCate(DishCategorize dishCategorize);

    // 搜索
    @Select("select * from `dish_cate` where `name` like CONCAT('%',#{subName},'%')")
    public List<DishCategorize>searchCateList(@Param("subName") String subName);

    // tools-根据分类名获取数据
    @Select("select * from `dish_cate` where name=#{name}")
    public DishCategorize getDishCategoryByName(@Param("name") String name);

    // 标签列表
    @Select("select * from `dish_tag`")
    public List<DishTagVo> DishTagList();

    // 搜索标签列表
    public List<DishTagVo> SearchDishTagList(@Param("subName") String subName,@Param("cateId")Integer cateId);

    // 添加标签
    @Insert("insert into `dish_tag` (name,categorize_id) values (#{name},#{cateId})")
    public void insertDishTag(@Param("name") String name,@Param("cateId")Integer cateId);

    // tools-根据标签名获取数据
    @Select("select * from `dish_tag` where name=#{tagName}")
    public DishTagVo getDishTagById(@Param("tagName") String tagName);

    // 删除标签
    public void deleteDishTagById(@Param("tagId") Integer tagId);

    // 批量删除标签
    public void BatchDeleteDishTagByIds(@Param("ids")List<Integer> ids);

    // 更新标签
    public void updateDishTagById(UpdateDishTagQuery query);


}
