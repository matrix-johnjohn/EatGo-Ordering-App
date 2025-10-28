package org.eatgo.menu;

import org.eatgo.menu.mapper.MenuMapper;
import org.eatgo.menu.service.MenuService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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
}
