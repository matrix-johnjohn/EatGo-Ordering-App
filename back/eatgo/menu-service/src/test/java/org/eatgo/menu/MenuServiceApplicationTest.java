package org.eatgo.menu;

import cn.hutool.db.PageResult;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.apache.ibatis.annotations.Delete;
import org.eatgo.common.domain.po.Dish;
import org.eatgo.common.domain.po.DishCategorize;
import org.eatgo.common.domain.po.DishTag;
import org.eatgo.common.domain.query.CollectionQuery;
import org.eatgo.common.domain.query.DishQuery;
import org.eatgo.common.domain.query.PageQuery;
import org.eatgo.menu.config.MinioConfig;
import org.eatgo.menu.mapper.MenuMapper;
import org.eatgo.menu.service.MenuService;
import org.eatgo.menu.util.MinioUtil;
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

    @Autowired
    MinioConfig minioConfig;

    @Autowired
    private MinioUtil minioUtil;

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

    @Test
    public void test9(){
        menuMapper.dishesByIds(List.of(1,2,3));
    }

    @Test
    public void test10(){
        menuMapper.deleteDishCateByIds(List.of(22));
    }

    @Test
    public void test11(){//添加分类接口
        DishCategorize dishCategorize=new DishCategorize();

        dishCategorize.setName("测试数据1");

        dishCategorize.setIcon("http://192.168.174.130:9000/eatgo/cate/icon/night_snack.svg");

        dishCategorize.setBanner(JSONUtil.toJsonStr(List.of("http://192.168.174.130:9000/eatgo/cate/banner/banner2.png","http://192.168.174.130:9000/eatgo/cate/banner/banner1.png")));
    }

    @Test
    public void test12(){
        List<String>list=List.of("/cate/banner/8592a4a0-d/drink1.png", "/cate/banner/8592a4a0-d/drink2.png");

        minioUtil.removeObject("/cate/icon/2bb4ea3f-8/2.png");

        for (String s : list) {
            minioUtil.removeObject(s);
        }
    }

    @Test
    public void test13(){
        menuMapper.searchCateList("测试").forEach(System.out::println);
    }
}
