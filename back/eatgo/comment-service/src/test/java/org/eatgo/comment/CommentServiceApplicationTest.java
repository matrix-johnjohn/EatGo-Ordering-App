package org.eatgo.comment;

import org.eatgo.comment.client.UserClient;
import org.eatgo.comment.mapper.CommentMapper;
import org.eatgo.common.domain.po.User;
import org.eatgo.common.domain.query.CommentQuery;
import org.eatgo.common.domain.query.UserComment;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.List;

@SpringBootTest
public class CommentServiceApplicationTest {

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private UserClient userClient;

    @Test
    public void test1(){
        Integer dishId=8;
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

        comments.forEach(System.out::println);
    }

    @Test
    public void test2(){
        commentMapper.uploadComment(new CommentQuery("测试上传数据",15,19));
    }
}
