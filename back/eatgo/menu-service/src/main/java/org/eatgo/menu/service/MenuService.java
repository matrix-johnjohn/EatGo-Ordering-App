package org.eatgo.menu.service;

import org.eatgo.common.domain.po.Dish;
import org.eatgo.common.domain.po.DishCategorize;
import org.eatgo.common.domain.po.DishTag;
import org.eatgo.common.domain.query.CollectionQuery;
import org.eatgo.common.domain.query.DishQuery;

import java.util.List;

public interface MenuService {
    public List<DishCategorize> cateList();//分类列表

    public List<DishTag> selectTagsByCateId(DishCategorize dishCategorize);//标签列表

    public List<Dish> dishListByCateAndTag(DishQuery dishQuery);

    public void plusCount(CollectionQuery collectionQuery);

    public void minusCount(CollectionQuery collectionQuery);
}
