package com.oneblog.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.oneblog.entity.Article;
import com.oneblog.entity.Comment;
import com.oneblog.mapper.CommentMapper;
import com.oneblog.service.ArticleService;
import com.oneblog.service.CommentService;
import com.oneblog.util.IpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {

    @Autowired
    private ArticleService articleService;

    @Override
    public List<Comment> getCommentsByArticleId(Long articleId) {
        return baseMapper.selectByArticleId(articleId);
    }

    @Override
    public List<Comment> getLatestComments(Integer limit) {
        return baseMapper.selectLatestComments(limit);
    }

    @Override
    @Transactional
    public void addComment(Comment comment, HttpServletRequest request) {
        comment.setCreateTime(LocalDateTime.now());
        comment.setStatus(0); // 待审核
        if (request != null) {
            comment.setIp(IpUtil.getIpAddress(request));
        }
        this.save(comment);
        
        // 更新文章评论数
        Article article = articleService.getById(comment.getArticleId());
        if (article != null) {
            article.setComments(article.getComments() + 1);
            articleService.updateById(article);
        }
    }

    @Override
    @Transactional
    public void replyComment(Long parentId, Comment comment, HttpServletRequest request) {
        comment.setParentId(parentId);
        addComment(comment, request);
    }
}

