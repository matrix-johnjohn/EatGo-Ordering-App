package org.eatgo.comment.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.eatgo.common.domain.query.UserComment;

import java.util.List;

@Mapper
public interface CommentMapper {

    @Select("select * from user_comment where dish_id=#{dishId}")
    public List<UserComment> commentList(Integer dishId);
}
