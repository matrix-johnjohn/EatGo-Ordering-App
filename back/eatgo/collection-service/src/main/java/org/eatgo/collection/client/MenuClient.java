package org.eatgo.collection.client;

import org.eatgo.common.domain.po.Dish;
import org.eatgo.common.domain.query.CollectionQuery;
import org.eatgo.common.domain.vo.ResultVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient("menu-service")
public interface MenuClient {

    @PostMapping("/menu/minus/count")
    public ResultVo<String> minus(@RequestBody CollectionQuery collectionQuery);

    @PostMapping("/menu/plus/count")
    public ResultVo<String> plus(@RequestBody CollectionQuery collectionQuery);

    @GetMapping("/menu/dishes/list")
    public ResultVo<List<Dish>> dishesList(@RequestParam("ids")List<Integer>ids);
}
