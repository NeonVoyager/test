package com.oneblog.controller;

import com.oneblog.common.Result;
import com.oneblog.entity.Comment;
import com.oneblog.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 评论控制器
 */
@RestController
@RequestMapping("/api/comments")
public class CommentController {

    @Autowired
    private CommentService commentService;

    /**
     * 获取文章评论列表
     */
    @GetMapping("/article/{articleId}")
    public Result<List<Comment>> getCommentsByArticleId(@PathVariable Long articleId) {
        List<Comment> comments = commentService.getCommentsByArticleId(articleId);
        return Result.success(comments);
    }

    /**
     * 添加评论
     */
    @PostMapping
    public Result<Void> addComment(@RequestBody Comment comment, HttpServletRequest request) {
        commentService.addComment(comment, request);
        return Result.success(null);
    }

    /**
     * 回复评论
     */
    @PostMapping("/reply/{parentId}")
    public Result<Void> replyComment(@PathVariable Long parentId, @RequestBody Comment comment, HttpServletRequest request) {
        commentService.replyComment(parentId, comment, request);
        return Result.success(null);
    }
}

