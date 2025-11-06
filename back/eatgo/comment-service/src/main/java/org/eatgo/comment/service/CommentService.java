package org.eatgo.comment.service;

import org.eatgo.common.domain.query.UserComment;

import java.util.List;

public interface CommentService {

    public List<UserComment>commentList(Integer dishId);
}
