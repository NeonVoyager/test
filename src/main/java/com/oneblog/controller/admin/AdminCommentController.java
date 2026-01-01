package com.oneblog.controller.admin;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.oneblog.common.Result;
import com.oneblog.entity.Comment;
import com.oneblog.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 后台评论管理控制器
 */
@RestController
@RequestMapping("/admin/comment")
public class AdminCommentController {

    @Autowired
    private CommentService commentService;

    /**
     * 获取评论列表
     */
    @GetMapping("/list")
    public Result<IPage<Comment>> getCommentList(
            @RequestParam(defaultValue = "1") Long current,
            @RequestParam(defaultValue = "10") Long size) {
        Page<Comment> page = new Page<>(current, size);
        IPage<Comment> result = commentService.page(page);
        return Result.success(result);
    }

    /**
     * 审核评论
     */
    @PostMapping("/audit/{id}")
    public Result<Void> auditComment(@PathVariable Long id, @RequestParam Integer status) {
        Comment comment = commentService.getById(id);
        if (comment != null) {
            comment.setStatus(status);
            commentService.updateById(comment);
        }
        return Result.success(null);
    }

    /**
     * 删除评论
     */
    @DeleteMapping("/{id}")
    public Result<Void> deleteComment(@PathVariable Long id) {
        Comment comment = commentService.getById(id);
        if (comment != null) {
            comment.setDeleted(1);
            commentService.updateById(comment);
        }
        return Result.success(null);
    }

    /**
     * 回复评论
     */
    @PostMapping("/reply/{id}")
    public Result<Void> replyComment(@PathVariable Long id, @RequestBody Comment reply) {
        commentService.replyComment(id, reply);
        return Result.success(null);
    }
}

