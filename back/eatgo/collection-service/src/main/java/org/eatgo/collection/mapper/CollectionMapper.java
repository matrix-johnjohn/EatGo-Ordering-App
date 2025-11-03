package org.eatgo.collection.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.eatgo.common.domain.po.Collection;
import org.eatgo.common.domain.po.Dish;
import org.eatgo.common.domain.query.CollectionQuery;

import java.util.List;

@Mapper
public interface CollectionMapper {

    @Insert("insert into user_collection (user_id,dish_id) values (#{userId},#{dishId})")
    public void InsertCollection(CollectionQuery collectionQuery);

    public int removeCollectionItem(CollectionQuery collectionQuery);

    public Collection selectCollectionItem(CollectionQuery collectionQuery);

    public List<Dish> dishes(List<Integer> ids);
}
