package org.eatgo.menu.service;

import org.eatgo.common.domain.po.DishCategorize;
import org.eatgo.common.domain.po.DishTag;

import java.util.List;

public interface MenuService {
    public List<DishCategorize> cateList();

    public List<DishTag> selectTagsByCateId(DishCategorize dishCategorize);
}
