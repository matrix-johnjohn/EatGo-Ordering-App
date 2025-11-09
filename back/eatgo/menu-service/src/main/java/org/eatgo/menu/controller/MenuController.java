package org.eatgo.menu.controller;

import lombok.RequiredArgsConstructor;
import org.eatgo.common.domain.po.Dish;
import org.eatgo.common.domain.po.DishCategorize;
import org.eatgo.common.domain.po.DishTag;
import org.eatgo.common.domain.query.CollectionQuery;
import org.eatgo.common.domain.query.DishQuery;
import org.eatgo.common.domain.query.PageQuery;
import org.eatgo.common.domain.vo.ResultVo;
import org.eatgo.menu.service.MenuService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/menu")
@RequiredArgsConstructor
public class MenuController {

    private final MenuService menuService;

    @GetMapping("/cate/list")
    public ResultVo<List<DishCategorize>>cateList(){
        List<DishCategorize> dishCategorizeList = menuService.cateList();

        return ResultVo.success("获取分类列表成功", dishCategorizeList);
    }

    @GetMapping("/tag/list/{cateId}")
    public ResultVo<List<DishTag>>findDishCategorizeList(@PathVariable("cateId") Integer cateId){
        DishCategorize dishCategorize=new DishCategorize();
        dishCategorize.setId(cateId);

        List<DishTag> dishTags=menuService.selectTagsByCateId(dishCategorize);

        return ResultVo.success("获取标签列表成功",dishTags);
    }

    @GetMapping("/dish/list/{cateId}/{tagId}")
    public ResultVo<List<Dish>>dishListByDishQuery(@PathVariable("cateId") Integer cateId,@PathVariable("tagId") Integer tagId){

        List<Dish>dishes=menuService.dishListByCateAndTag(new DishQuery(cateId, tagId));

        return ResultVo.success("获取菜品列表成功",dishes);
    }

    @PostMapping("/minus/count")
    public ResultVo<String> minus(@RequestBody CollectionQuery collectionQuery){
        menuService.minusCount(collectionQuery);

        return ResultVo.success("次数减少成功",null);
    }

    @PostMapping("/plus/count")
    public ResultVo<String> plus(@RequestBody CollectionQuery collectionQuery){
        menuService.plusCount(collectionQuery);

        return ResultVo.success("次数增加成功",null);
    }

    @GetMapping("/dishes/list")
    public ResultVo<List<Dish>> dishesList(@RequestParam("ids")List<Integer>ids){
        List<Dish>dishes=menuService.dishesListByids(ids);
        return ResultVo.success("用户收藏列表",dishes);
    }

    @GetMapping("/popular/list/{pageNum}/{pageSize}")
    public ResultVo<List<Dish>> popularList(
            @PathVariable("pageNum")Integer pageNum,
            @PathVariable("pageSize")Integer pageSize){

        List<Dish>dishes=menuService.recommandList(new PageQuery(pageNum,pageSize));

        return ResultVo.success("收藏热门",dishes);
    }

    @GetMapping("/dish/detail/{dishId}")
    public ResultVo<Dish> getDishById(@PathVariable("dishId") Integer dishId){
        Dish dish=menuService.findById(dishId);

        return ResultVo.success("成功获取菜品数据",dish);
    }

    @GetMapping("/dish/list")
    public ResultVo<List<Dish>>DishList(){
        return ResultVo.success("全部菜品数据",menuService.dishList());
    }
}
