package org.eatgo.comment.service.impl;

import lombok.RequiredArgsConstructor;
import org.eatgo.comment.client.UserClient;
import org.eatgo.comment.mapper.CommentMapper;
import org.eatgo.comment.service.CommentService;
import org.eatgo.common.domain.po.User;
import org.eatgo.common.domain.query.CommentQuery;
import org.eatgo.common.domain.query.UserComment;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentMapper commentMapper;

    private final UserClient userClient;

    @Override
    public List<UserComment> commentList(Integer dishId) {

        //获取用户列表
        List<User> userList=userClient.userList().getData();

        //获取评论列表
        List<UserComment>comments=commentMapper.commentList(dishId);

        for(UserComment comment:comments){
            for (User user:userList) {
                Integer userId=comment.getUserId();

                if(userId.equals(user.getId())){
                    comment.setAvatar(user.getAvatar());
                    comment.setUsername(user.getUsername());
                }
            }
        }

        return comments;
    }

    @Override
    public void uploadComment(CommentQuery commentQuery) {
        commentMapper.uploadComment(commentQuery);
    }
}
