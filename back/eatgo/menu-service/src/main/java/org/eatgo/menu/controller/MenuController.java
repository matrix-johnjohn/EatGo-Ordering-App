package org.eatgo.menu.controller;


import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONUtil;
import lombok.RequiredArgsConstructor;
import org.eatgo.common.domain.dto.DishDto;
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
import org.eatgo.common.domain.vo.ResultVo;
import org.eatgo.menu.service.MenuService;
import org.eatgo.menu.util.MinioUtil;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/menu")
@RequiredArgsConstructor
public class MenuController {

    private final MenuService menuService;

    private final MinioUtil minioUtil;

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
    public ResultVo<List<Dish>> dishListByDishQuery(@PathVariable("cateId") Integer cateId,@PathVariable("tagId") Integer tagId){

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

    // 后台管理
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

    @DeleteMapping("/delete/dish/cate/{id}")
    public ResultVo<String>deleteDishCate(@PathVariable("id") Integer id){

        DishCategorize cate = new DishCategorize();

        cate.setId(id);

        menuService.deleteDishCateById(cate);

        return ResultVo.success("消息提示","菜品删除成功");
    }

    @DeleteMapping("/delete/dish/cate/list")
    public ResultVo<String>deleteDishCateList(@RequestParam("ids") List<Integer> ids){
        menuService.deleteDishCateByIds(ids);

        return ResultVo.success("消息提示","菜品列表删除成功");
    }

    @PutMapping("/upload")
    public ResultVo<String>uploadTest(@RequestParam("icon")MultipartFile icon,@RequestParam("banner")MultipartFile[] banner,@RequestParam("name")String name){

        menuService.addCate(name,icon,banner);

        return ResultVo.success("文件上传测试","上传成功");
    }

    @PutMapping("/remove/banner/{idx}")
    public ResultVo<String>removeBanner(@RequestBody DishCategorize dishCategorize,
                                        @PathVariable("idx") Integer idx){
         menuService.removeBanner(dishCategorize,idx);

        return ResultVo.success("消息提示","轮播图删除成功");
    }

    @PutMapping("/update/cate")
    public ResultVo<String>updateCate(
            @RequestParam("cate")String dishCategorize,
            @RequestParam(value = "icon",required = false)MultipartFile icon,
            @RequestParam(value="banner",required = false)MultipartFile[]banner
    ){
        DishCategorize dishCate = JSONUtil.toBean(dishCategorize, DishCategorize.class);

        menuService.updateCate(dishCate,icon,banner);

        return ResultVo.success("消息提示","分类更新成功");
    }

    @GetMapping("/search/cate/list/{subString}")
    public ResultVo<List<DishCategorize>>searchCateList(@PathVariable(value="subString",required = false)String subString){
        List<DishCategorize> cateList = menuService.searchCateList(subString);

        return ResultVo.success("",cateList);
    }

    @GetMapping("/dish/tag/all/list")
    public ResultVo<List<DishTagVo>>dishTagVoList(){
        List<DishTagVo> dishTagVos=menuService.DishTagVoList();

        return ResultVo.success("菜品标签数据列表",dishTagVos);
    }

    @GetMapping("/search/tag/list")
    public ResultVo<List<DishTagVo>>searchDishTagVoList(
            @RequestParam(value="subName",required = false) String subName,
            @RequestParam(value="cateId",required = false) Integer cateId){
        List<DishTagVo> dishTagVos=menuService.SearchDishTagVoList(subName,cateId);

        return ResultVo.success("搜索成功",dishTagVos);
    }

    @PutMapping("/insert/dish/tag")
    public ResultVo<String>insertDishTag(
            @RequestParam("name")String name,
            @RequestParam("cateId")Integer cateId
    ){
        menuService.insertDishTag(name,cateId);
        return ResultVo.success("插入数据成功",null);
    }

    @DeleteMapping("/delete/dish/tag/{tagId}")
    public ResultVo<String>removeDishTag(@PathVariable("tagId")Integer tagId){
        menuService.deleteDishTagById(tagId);

        return ResultVo.success("消息提示","数据删除成功");
    }

    @DeleteMapping("/batch/delete/dish/tag")
    public ResultVo<String>BatchRemoveDishTag(@RequestBody List<Integer>ids){
        System.out.println(ids);
        menuService.BatchDeleteDishTag(ids);
        return ResultVo.success("消息提示","数据批量删除成功");
    }

    @PutMapping("/update/dish/tag/by")
    public ResultVo<String>updateDishTagById(@RequestBody UpdateDishTagQuery updateDishTagQuery){
        menuService.updateDishTagById(updateDishTagQuery);
        return ResultVo.success("消息提示","标签信息更新成功");
    }

    /**
     * 菜品管理
    * */
    // 菜品列表
    @GetMapping("/dish/detail/list")
    public ResultVo<List<DishVo>>dishDetailList(){
        List<DishVo> dishVos=menuService.dishVoList();

        return ResultVo.success("菜品详情列表",dishVos);
    }
    // 搜索菜品
    @PostMapping("/search/dish/detail/list")
    public ResultVo<List<DishVo>>searchDishDetailList(@RequestBody DishSearchForm form){
        List<DishVo> dishVos=menuService.searchDishVoList(form);

        return ResultVo.success("搜索菜品列表",dishVos);
    }

    // 上传菜品
    @PutMapping(value="/put/dish/upload")
    public ResultVo<String>putDishUpload(
            @RequestParam("dishImg")MultipartFile dishImg,
            @RequestParam("dishData")String dishData
    ){
        DishDto dishDto=JSONUtil.toBean(dishData, DishDto.class);

        menuService.addDish(dishImg,dishDto);

        return ResultVo.success("上传成功",null);
    }
    /*
     * 删除菜品
     * */
    @DeleteMapping("/delete/dish/vo")
    public ResultVo<String>deleteDish(@RequestBody DishVo vo){
        menuService.deleteDish(vo);

        return ResultVo.success("菜品删除成功",null);
    }

    /*
    * 批量删除菜品
    * */
    @DeleteMapping("/batch/delete/dish/vo")
    public ResultVo<String>BatchDeleteDish(@RequestBody List<DishVo>list){
        menuService.BatchDeleteDish(list);
        return ResultVo.success("菜品批量删除成功",null);
    }

    /*
    * 更新菜品
    * */
    @PutMapping("/update/dish/vo")
    public ResultVo<String>updateDish(
            @RequestParam(value = "image",required = false)MultipartFile image
            ,@RequestParam(value = "dish",required = false) String dish
    ){

        DishVo vo=JSONUtil.toBean(dish, DishVo.class);

        menuService.updateDish(image,vo);

        return ResultVo.success("菜品数据更新成功",null);
    }
}
