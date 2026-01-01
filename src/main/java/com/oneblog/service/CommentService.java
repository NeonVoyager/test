package com.oneblog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.oneblog.entity.Comment;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface CommentService extends IService<Comment> {
    List<Comment> getCommentsByArticleId(Long articleId);
    List<Comment> getLatestComments(Integer limit);
    void addComment(Comment comment, HttpServletRequest request);
    void replyComment(Long parentId, Comment comment, HttpServletRequest request);
}

