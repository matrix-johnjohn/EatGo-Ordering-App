package org.eatgo.menu;

import cn.hutool.db.PageResult;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.eatgo.common.domain.po.Dish;
import org.eatgo.common.domain.po.DishCategorize;
import org.eatgo.common.domain.po.DishTag;
import org.eatgo.common.domain.query.CollectionQuery;
import org.eatgo.common.domain.query.DishQuery;
import org.eatgo.common.domain.query.PageQuery;
import org.eatgo.menu.mapper.MenuMapper;
import org.eatgo.menu.service.MenuService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class MenuServiceApplicationTest {

    @Autowired
    MenuMapper menuMapper;

    @Autowired
    MenuService menuService;

    @Test
    public void test1(){
        menuService.cateList().forEach(System.out::println);
    }

    @Test
    public void test2(){
        DishCategorize cate=new DishCategorize();
        cate.setId(1);
        List<DishTag> dishTags=menuMapper.tagList(cate);
        dishTags.forEach(System.out::println);
    }

    @Test
    public void test3() {
        DishQuery dishQuery=new DishQuery();
        dishQuery.setCategorizeId(1);
        for (Dish dish : menuMapper.dishList(dishQuery)) {
            System.out.println(dish);
        }
    }

    @Test
    public void test4() {
        CollectionQuery query=new CollectionQuery();

        query.setDishId(1);

        menuMapper.plusCount(query);
    }

    @Test
    public void test5() {
        CollectionQuery query=new CollectionQuery();

        query.setDishId(1);

        menuMapper.minusCount(query);
    }

    @Test
    public void test6() {
        ArrayList<Integer> list=new ArrayList<>();

        list.add(3);
        list.add(1);
        list.add(2);

        List<Dish>dishes=menuMapper.dishesByIds(list);

        dishes.forEach(System.out::println);
    }

    @Test
    public void test7() {
        PageQuery pageQuery=new PageQuery(2, 5);

        menuService.recommandList(pageQuery).forEach(System.out::println);
    }

    @Test
    public void test8(){
        Dish dish=menuMapper.getDishById(2);
    }
}
