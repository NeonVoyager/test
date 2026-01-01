package com.oneblog.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.oneblog.entity.Article;

import java.util.List;

public interface ArticleService extends IService<Article> {
    IPage<Article> getArticleList(Long current, Long size);
    Article getArticleDetail(Long id);
    void increaseViews(Long id);
    void likeArticle(Long id);
    List<Article> getHotArticles(Integer limit);
    List<Article> getTopArticles(Integer limit);
    List<Article> getRecommendArticles(Long articleId, Integer limit);
    IPage<Article> searchArticles(String keyword, Long current, Long size);
    IPage<Article> getArticlesByCategory(Long categoryId, Long current, Long size);
    IPage<Article> getArticlesByTag(String tagName, Long current, Long size);
    List<Article> getArchiveArticles(String year, String month);
}

