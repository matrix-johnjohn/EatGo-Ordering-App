package org.eatgo.collection.service.impl;

import cn.hutool.core.util.ObjectUtil;
import lombok.RequiredArgsConstructor;
import org.eatgo.collection.mapper.CollectionMapper;
import org.eatgo.collection.service.CollectionService;
import org.eatgo.common.domain.po.Collection;
import org.eatgo.common.domain.query.CollectionQuery;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CollectionServiceImpl implements CollectionService {

    private final CollectionMapper collectionMapper;

    @Override
    public void collect(CollectionQuery collectionQuery) {
        Collection collectionRecord=collectionMapper.selectCollectionItem(collectionQuery);//查找是否有这条数据

        boolean isEmptyObject=ObjectUtil.isEmpty(collectionRecord);

        if(isEmptyObject){//记录为空,添加数据
            collectionMapper.InsertCollection(collectionQuery);

            //TODO:增加dish列表的count
        }
    }

    @Override
    public void cancelCollect(CollectionQuery collectionQuery) {
        Collection collectionRecord=collectionMapper.selectCollectionItem(collectionQuery);//查找是否有这条数据

        boolean isEmptyObject=ObjectUtil.isEmpty(collectionRecord);

        if(!isEmptyObject){//数据非空,删除数据
            collectionMapper.removeCollectionItem(collectionQuery);

            //TODO:减少dish列表的count
        }
    }
}
