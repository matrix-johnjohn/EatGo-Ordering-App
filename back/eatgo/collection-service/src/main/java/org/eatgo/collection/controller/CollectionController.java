package org.eatgo.collection.controller;

import lombok.RequiredArgsConstructor;
import org.eatgo.collection.service.CollectionService;
import org.eatgo.common.domain.po.Collection;
import org.eatgo.common.domain.po.Dish;
import org.eatgo.common.domain.query.CollectionQuery;
import org.eatgo.common.domain.vo.ResultVo;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/collection")
public class CollectionController {

    private final CollectionService collectionService;

    @PostMapping("/collect")
    public ResultVo<String> collect(@RequestBody CollectionQuery collectionQuery){
        collectionService.collect(collectionQuery);

        return ResultVo.success("添加收藏成功",null);
    }

    @PostMapping("/cancel/collect")
    public ResultVo<String> cancelCollect(@RequestBody CollectionQuery collectionQuery){
        collectionService.cancelCollect(collectionQuery);

        return ResultVo.success("删除收藏成功",null);
    }

    @GetMapping("/list/{userId}")
    public ResultVo<List<Collection>>collectionList(@PathVariable("userId") Integer userId){
        return ResultVo.success("收藏数据获取成功",collectionService.collectionList(userId));
    }

    @GetMapping("/dish/list/{userId}")
    public ResultVo<List<Dish>>collectionDishList(@PathVariable("userId") Integer userId){

        List<Dish>dishes=collectionService.collectionDishList(userId);

        return ResultVo.success("",dishes);
    }
}
