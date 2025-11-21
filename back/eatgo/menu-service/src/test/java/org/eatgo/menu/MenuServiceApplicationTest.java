package org.eatgo.menu;

import cn.hutool.db.PageResult;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.apache.ibatis.annotations.Delete;
import org.eatgo.common.domain.form.DishSearchForm;
import org.eatgo.common.domain.po.Dish;
import org.eatgo.common.domain.po.DishCategorize;
import org.eatgo.common.domain.po.DishTag;
import org.eatgo.common.domain.query.CollectionQuery;
import org.eatgo.common.domain.query.DishQuery;
import org.eatgo.common.domain.query.PageQuery;
import org.eatgo.common.domain.query.UpdateDishTagQuery;
import org.eatgo.common.domain.vo.DishTagVo;
import org.eatgo.common.domain.vo.DishVo;
import org.eatgo.menu.config.MinioConfig;
import org.eatgo.menu.mapper.MenuMapper;
import org.eatgo.menu.service.MenuService;
import org.eatgo.menu.util.MinioUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
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
    private JedisPool jedisPool;

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
        Jedis jedis=jedisPool.getResource();

        List<DishCategorize>cateList=menuMapper.cateList();

        for(DishCategorize cate:cateList) {
            String jsonStr=JSONUtil.toJsonStr(cate);

            System.out.println(jsonStr);

            jedis.set("cate:dish:"+cate.getId(),jsonStr);
        }

        jedis.close();
    }

    @Test
    public void test14(){

        // 数据库读取分类信息
        List<DishTagVo>dishTag=menuMapper.DishTagList();

        Jedis jedis=jedisPool.getResource();

        for (DishTagVo dishTagVo : dishTag){
            // 数据库缓存根据分类id获取分类名
            String dishCateJSON=jedis.get("cate:dish:" + dishTagVo.getCategorizeId());
            DishCategorize dishCate=JSONUtil.toBean(dishCateJSON, DishCategorize.class);
            dishTagVo.setCateName(dishCate.getName());
        }

        for (DishTagVo dishTagVo : dishTag) {
            String jsonStr=JSONUtil.toJsonStr(dishTagVo);
            jedis.set("tag:dish:" + dishTagVo.getId(),jsonStr);
        }

        jedis.close();
    }

    @Test
    public void test15(){
        List<DishTagVo>dishTag=menuMapper.SearchDishTagList("传统",1);

        Jedis jedis=jedisPool.getResource();

        for (DishTagVo dishTagVo : dishTag){
            // 数据库缓存根据分类id获取分类名
            String dishCateJSON=jedis.get("cate:dish:" + dishTagVo.getCategorizeId());
            DishCategorize dishCate=JSONUtil.toBean(dishCateJSON, DishCategorize.class);
            dishTagVo.setCateName(dishCate.getName());
        }

        dishTag.forEach(System.out::println);

        jedis.close();
    }

    @Test
    public void test16(){
        // 数据库写入数据
        menuMapper.insertDishTag("test",1);

        // 数据库缓存写入数据
        Jedis jedis=jedisPool.getResource();

        DishTagVo tag=menuMapper.getDishTagById("test");

        String key="cate:dish:"+1;

        String json=jedis.get(key);

        DishCategorize dishCate=JSONUtil.toBean(json, DishCategorize.class);

        tag.setCateName(dishCate.getName());

        String dataStr=JSONUtil.toJsonStr(tag);

        jedis.set("tag:dish:"+tag.getId(), dataStr);
    }

    @Test
    public void test17(){
        menuMapper.updateDishTagById(new UpdateDishTagQuery(28,"测试数据10",28));
    }

    @Test
    public void test18(){
        List<DishVo> dishList=menuMapper.dishDetailList();

        Jedis jedis=jedisPool.getResource();
        for(DishVo dishVo:dishList){
            Integer cateId=dishVo.getCategorizeId();
            Integer tagId=dishVo.getTagId();

            List<String>keys=jedis.mget("cate:dish:"+cateId, "tag:dish:"+tagId);

            DishTag dishTag=JSONUtil.toBean(keys.get(1), DishTag.class);
            String tagName=dishTag.getName();
            DishCategorize dishCate=JSONUtil.toBean(keys.get(0), DishCategorize.class);
            String cateName=dishCate.getName();

            dishVo.setCateName(cateName);
            dishVo.setTagName(tagName);
        }

        for (DishVo dishVo : dishList) {
            jedis.set("dish:dish:"+dishVo.getId(),JSONUtil.toJsonStr(dishVo));
        }
        jedis.close();
    }

    @Test
    public void test19(){
        Jedis jedis = jedisPool.getResource();

        List<String>list=jedis.mget("cate:dish:1", "tag:dish:1");

        list.forEach(System.out::println);

        jedis.close();
    }

    @Test
    public void test20(){
        DishSearchForm form=new DishSearchForm();

        form.setTitle("鸡腿");

        form.setDishCateId(1);

        form.setDishTagId(2);

        List<DishVo>dishList=menuMapper.searchDishDetailList(form);

        Jedis jedis=jedisPool.getResource();
        for(DishVo dishVo:dishList){
            Integer cateId=dishVo.getCategorizeId();
            Integer tagId=dishVo.getTagId();

            List<String>keys=jedis.mget("cate:dish:"+cateId, "tag:dish:"+tagId);

            DishTag dishTag=JSONUtil.toBean(keys.get(1), DishTag.class);
            String tagName=dishTag.getName();
            DishCategorize dishCate=JSONUtil.toBean(keys.get(0), DishCategorize.class);
            String cateName=dishCate.getName();

            dishVo.setCateName(cateName);
            dishVo.setTagName(tagName);
        }
        jedis.close();

        for (DishVo dishVo : dishList) {
            System.out.println(dishVo);
        }
    }
}
