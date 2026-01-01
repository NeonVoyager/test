package com.oneblog.controller.admin;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.oneblog.common.Result;
import com.oneblog.entity.Article;
import com.oneblog.listener.ArticlePublishListener;
import com.oneblog.service.ArticleService;
import com.oneblog.service.KeywordLinkService;
import com.oneblog.util.MarkdownUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 后台文章管理控制器
 */
@RestController
@RequestMapping("/admin/article")
public class AdminArticleController {

    @Autowired
    private ArticleService articleService;

    @Autowired
    private KeywordLinkService keywordLinkService;

    @Autowired
    private ArticlePublishListener articlePublishListener;

    /**
     * 获取文章列表（分页）
     */
    @GetMapping("/list")
    public Result<IPage<Article>> getArticleList(
            @RequestParam(defaultValue = "1") Long current,
            @RequestParam(defaultValue = "10") Long size) {
        Page<Article> page = new Page<>(current, size);
        IPage<Article> result = articleService.page(page);
        return Result.success(result);
    }

    /**
     * 获取文章详情
     */
    @GetMapping("/{id}")
    public Result<Article> getArticle(@PathVariable Long id) {
        Article article = articleService.getById(id);
        return Result.success(article);
    }

    /**
     * 发布/更新文章
     */
    @PostMapping
    public Result<Void> saveArticle(@RequestBody Article article) {
        if (article.getId() == null) {
            article.setCreateTime(LocalDateTime.now());
            article.setViews(0);
            article.setLikes(0);
            article.setComments(0);
        }
        
        // 转换Markdown为HTML
        if (article.getContent() != null) {
            String htmlContent = MarkdownUtil.markdownToHtml(article.getContent());
            // 处理关键字链接
            htmlContent = keywordLinkService.processArticleContent(htmlContent);
            article.setHtmlContent(htmlContent);
        }
        
        if (article.getStatus() == 1 && article.getPublishTime() == null) {
            article.setPublishTime(LocalDateTime.now());
        }
        
        article.setUpdateTime(LocalDateTime.now());
        article.setDeleted(0);
        articleService.saveOrUpdate(article);
        
        // 如果文章已发布，发送邮件通知订阅用户
        if (article.getStatus() == 1) {
            articlePublishListener.onArticlePublished(article);
        }
        
        return Result.success(null);
    }

    /**
     * 删除文章
     */
    @DeleteMapping("/{id}")
    public Result<Void> deleteArticle(@PathVariable Long id) {
        Article article = articleService.getById(id);
        if (article != null) {
            article.setDeleted(1);
            articleService.updateById(article);
        }
        return Result.success(null);
    }

    /**
     * 批量删除文章
     */
    @DeleteMapping("/batch")
    public Result<Void> batchDeleteArticles(@RequestBody List<Long> ids) {
        for (Long id : ids) {
            Article article = articleService.getById(id);
            if (article != null) {
                article.setDeleted(1);
                articleService.updateById(article);
            }
        }
        return Result.success(null);
    }
}

