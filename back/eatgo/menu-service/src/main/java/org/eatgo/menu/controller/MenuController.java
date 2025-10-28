package org.eatgo.menu.controller;

import lombok.RequiredArgsConstructor;
import org.eatgo.common.domain.po.DishCategorize;
import org.eatgo.common.domain.po.DishTag;
import org.eatgo.common.domain.vo.ResultVo;
import org.eatgo.menu.service.MenuService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
