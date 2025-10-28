package org.eatgo.menu.mapper;

import org.apache.ibatis.annotations.Select;
import org.eatgo.common.domain.po.DishCategorize;
import org.eatgo.common.domain.po.DishTag;

import java.util.List;


public interface MenuMapper {
    @Select("select * from dish_cate")
    public List<DishCategorize> cateList();


    public List<DishTag>tagList(DishCategorize dishCategorize);
}
