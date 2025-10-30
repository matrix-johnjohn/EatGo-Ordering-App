package org.eatgo.collection.service;

import org.eatgo.common.domain.query.CollectionQuery;

public interface CollectionService {

    public void collect(CollectionQuery collectionQuery);//收藏接口

    public void cancelCollect(CollectionQuery collectionQuery);//取消收藏接口
}
