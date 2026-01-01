package com.oneblog.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.oneblog.common.Result;
import com.oneblog.entity.Article;
import com.oneblog.entity.Category;
import com.oneblog.entity.Comment;
import com.oneblog.entity.Tag;
import com.oneblog.service.ArticleService;
import com.oneblog.service.CategoryService;
import com.oneblog.service.CommentService;
import com.oneblog.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 前台首页控制器
 */
@RestController
@RequestMapping("/api")
public class IndexController {

    @Autowired
    private ArticleService articleService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private TagService tagService;

    @Autowired
    private CommentService commentService;

    /**
     * 获取文章列表（分页）
     */
    @GetMapping("/articles")
    public Result<IPage<Article>> getArticles(
            @RequestParam(defaultValue = "1") Long current,
            @RequestParam(defaultValue = "10") Long size) {
        IPage<Article> page = articleService.getArticleList(current, size);
        return Result.success(page);
    }

    /**
     * 获取文章详情
     */
    @GetMapping("/article/{id}")
    public Result<Article> getArticleDetail(@PathVariable Long id) {
        Article article = articleService.getArticleDetail(id);
        if (article == null) {
            return Result.error("文章不存在");
        }
        articleService.increaseViews(id);
        return Result.success(article);
    }

    /**
     * 点赞文章
     */
    @PostMapping("/article/{id}/like")
    public Result<Void> likeArticle(@PathVariable Long id) {
        articleService.likeArticle(id);
        return Result.success(null);
    }

    /**
     * 获取热门文章
     */
    @GetMapping("/articles/hot")
    public Result<List<Article>> getHotArticles(@RequestParam(defaultValue = "10") Integer limit) {
        List<Article> articles = articleService.getHotArticles(limit);
        return Result.success(articles);
    }

    /**
     * 获取置顶文章（轮播图）
     */
    @GetMapping("/articles/top")
    public Result<List<Article>> getTopArticles(@RequestParam(defaultValue = "5") Integer limit) {
        List<Article> articles = articleService.getTopArticles(limit);
        return Result.success(articles);
    }

    /**
     * 获取最新评论
     */
    @GetMapping("/comments/latest")
    public Result<List<Comment>> getLatestComments(@RequestParam(defaultValue = "10") Integer limit) {
        List<Comment> comments = commentService.getLatestComments(limit);
        return Result.success(comments);
    }

    /**
     * 获取分类列表
     */
    @GetMapping("/categories")
    public Result<List<Category>> getCategories() {
        List<Category> categories = categoryService.list();
        return Result.success(categories);
    }

    /**
     * 获取标签列表
     */
    @GetMapping("/tags")
    public Result<List<Tag>> getTags() {
        List<Tag> tags = tagService.list();
        return Result.success(tags);
    }

    /**
     * 搜索文章
     */
    @GetMapping("/articles/search")
    public Result<IPage<Article>> searchArticles(
            @RequestParam String keyword,
            @RequestParam(defaultValue = "1") Long current,
            @RequestParam(defaultValue = "10") Long size) {
        IPage<Article> page = articleService.searchArticles(keyword, current, size);
        return Result.success(page);
    }

    /**
     * 按分类获取文章
     */
    @GetMapping("/articles/category/{categoryId}")
    public Result<IPage<Article>> getArticlesByCategory(
            @PathVariable Long categoryId,
            @RequestParam(defaultValue = "1") Long current,
            @RequestParam(defaultValue = "10") Long size) {
        IPage<Article> page = articleService.getArticlesByCategory(categoryId, current, size);
        return Result.success(page);
    }

    /**
     * 按标签获取文章
     */
    @GetMapping("/articles/tag/{tagName}")
    public Result<IPage<Article>> getArticlesByTag(
            @PathVariable String tagName,
            @RequestParam(defaultValue = "1") Long current,
            @RequestParam(defaultValue = "10") Long size) {
        IPage<Article> page = articleService.getArticlesByTag(tagName, current, size);
        return Result.success(page);
    }

    /**
     * 获取归档文章
     */
    @GetMapping("/articles/archive")
    public Result<List<Article>> getArchiveArticles(
            @RequestParam(required = false) String year,
            @RequestParam(required = false) String month) {
        List<Article> articles = articleService.getArchiveArticles(year, month);
        return Result.success(articles);
    }

    /**
     * 获取推荐文章（基于标签）
     */
    @GetMapping("/articles/recommend/{articleId}")
    public Result<List<Article>> getRecommendArticles(
            @PathVariable Long articleId,
            @RequestParam(defaultValue = "5") Integer limit) {
        List<Article> articles = articleService.getRecommendArticles(articleId, limit);
        return Result.success(articles);
    }

    /**
     * 获取侧边栏数据
     */
    @GetMapping("/sidebar")
    public Result<Map<String, Object>> getSidebarData() {
        Map<String, Object> data = new HashMap<>();
        data.put("hotArticles", articleService.getHotArticles(10));
        data.put("latestComments", commentService.getLatestComments(10));
        data.put("tags", tagService.list());
        return Result.success(data);
    }
}

