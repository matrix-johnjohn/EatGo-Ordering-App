package org.eatgo.comment.service;

import org.eatgo.common.domain.query.CommentQuery;
import org.eatgo.common.domain.query.UserComment;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface CommentService {

    public List<UserComment>commentList(Integer dishId);

    public void uploadComment(CommentQuery commentQuery);
}
