package org.eatgo.menu.mapper;

import org.apache.ibatis.annotations.Select;
import org.eatgo.common.domain.po.DishCategorize;
import java.util.List;


public interface MenuMapper {
    @Select("select * from dish_cate")
    public List<DishCategorize> cateList();
}
