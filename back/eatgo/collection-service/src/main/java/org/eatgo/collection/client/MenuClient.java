package org.eatgo.collection.client;

import org.eatgo.common.domain.query.CollectionQuery;
import org.eatgo.common.domain.vo.ResultVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient("menu-service")
public interface MenuClient {

    @PostMapping("/menu/minus/count")
    public ResultVo<String> minus(@RequestBody CollectionQuery collectionQuery);

    @PostMapping("/menu/plus/count")
    public ResultVo<String> plus(@RequestBody CollectionQuery collectionQuery);
}
