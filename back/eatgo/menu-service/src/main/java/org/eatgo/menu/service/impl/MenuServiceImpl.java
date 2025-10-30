package org.eatgo.menu.service.impl;

import lombok.RequiredArgsConstructor;
import org.eatgo.common.domain.po.Dish;
import org.eatgo.common.domain.po.DishCategorize;
import org.eatgo.common.domain.po.DishTag;
import org.eatgo.common.domain.query.CollectionQuery;
import org.eatgo.common.domain.query.DishQuery;
import org.eatgo.menu.mapper.MenuMapper;
import org.eatgo.menu.service.MenuService;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MenuServiceImpl implements MenuService {

    private final MenuMapper menuMapper;

    @Override
    public List<DishCategorize> cateList() {//获取分类列表
        return menuMapper.cateList();
    }

    @Override
    public List<DishTag> selectTagsByCateId(DishCategorize dishCategorize) {//根据分类Id获取标签列表
        return menuMapper.tagList(dishCategorize);
    }

    @Override
    public List<Dish> dishListByCateAndTag(DishQuery dishQuery) {//首页菜品列表是数据
        return menuMapper.dishList(dishQuery);
    }

    @Override
    public void plusCount(CollectionQuery collectionQuery) {
        menuMapper.plusCount(collectionQuery);
    }

    @Override
    public void minusCount(CollectionQuery collectionQuery) {
        menuMapper.minusCount(collectionQuery);
    }
}
