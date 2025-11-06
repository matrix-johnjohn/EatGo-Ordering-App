package org.eatgo.comment.controller;

import lombok.RequiredArgsConstructor;
import org.eatgo.comment.service.CommentService;
import org.eatgo.common.domain.query.UserComment;
import org.eatgo.common.domain.vo.ResultVo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/comment")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @GetMapping("/list/{dishId}")
    public ResultVo<List<UserComment>> commentList(@PathVariable("dishId") Integer dishId){

        List<UserComment> comments = commentService.commentList(dishId);

        return ResultVo.success("列表获取成功",comments);
    }
}
