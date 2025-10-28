package org.eatgo.menu;

import org.eatgo.common.domain.po.DishCategorize;
import org.eatgo.common.domain.po.DishTag;
import org.eatgo.menu.mapper.MenuMapper;
import org.eatgo.menu.service.MenuService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class MenuServiceApplicationTest {

    @Autowired
    MenuMapper menuMapper;

    @Autowired
    MenuService menuService;

    @Test
    public void test1(){
        menuService.cateList().forEach((e)->{
            System.out.println(e);
        });
    }

    @Test
    public void test2(){
        DishCategorize cate=new DishCategorize();

        cate.setId(2);

        List<DishTag> dishTags=menuMapper.tagList(cate);

        dishTags.forEach((e)->{
            System.out.println(e);
        });
    }
}
