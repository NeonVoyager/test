package com.oneblog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.oneblog.entity.Article;
import com.oneblog.mapper.ArticleMapper;
import com.oneblog.service.ArticleService;
import com.oneblog.util.MarkdownUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {

    @Override
    public IPage<Article> getArticleList(Long current, Long size) {
        Page<Article> page = new Page<>(current, size);
        LambdaQueryWrapper<Article> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Article::getStatus, 1)
               .eq(Article::getDeleted, 0)
               .orderByDesc(Article::getIsTop)
               .orderByDesc(Article::getPublishTime);
        return this.page(page, wrapper);
    }

    @Override
    public Article getArticleDetail(Long id) {
        Article article = this.getById(id);
        if (article != null && article.getDeleted() == 0) {
            // 转换Markdown为HTML
            if (article.getContent() != null && article.getHtmlContent() == null) {
                article.setHtmlContent(MarkdownUtil.markdownToHtml(article.getContent()));
            }
        }
        return article;
    }

    @Override
    @Transactional
    public void increaseViews(Long id) {
        Article article = this.getById(id);
        if (article != null) {
            article.setViews(article.getViews() + 1);
            this.updateById(article);
        }
    }

    @Override
    @Transactional
    public void likeArticle(Long id) {
        Article article = this.getById(id);
        if (article != null) {
            article.setLikes(article.getLikes() + 1);
            this.updateById(article);
        }
    }

    @Override
    public List<Article> getHotArticles(Integer limit) {
        return baseMapper.selectHotArticles(limit);
    }

    @Override
    public List<Article> getTopArticles(Integer limit) {
        LambdaQueryWrapper<Article> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Article::getStatus, 1)
               .eq(Article::getIsTop, 1)
               .eq(Article::getDeleted, 0)
               .orderByDesc(Article::getPublishTime)
               .last("LIMIT " + limit);
        return this.list(wrapper);
    }

    @Override
    public List<Article> getRecommendArticles(Long articleId, Integer limit) {
        Article article = this.getById(articleId);
        if (article == null || article.getTags() == null) {
            return List.of();
        }
        return baseMapper.selectRecommendArticles(articleId, article.getTags(), limit);
    }

    @Override
    public IPage<Article> searchArticles(String keyword, Long current, Long size) {
        Page<Article> page = new Page<>(current, size);
        List<Article> articles = baseMapper.searchArticles(keyword);
        page.setRecords(articles);
        page.setTotal(articles.size());
        return page;
    }

    @Override
    public IPage<Article> getArticlesByCategory(Long categoryId, Long current, Long size) {
        Page<Article> page = new Page<>(current, size);
        List<Article> articles = baseMapper.selectByCategory(categoryId);
        page.setRecords(articles);
        page.setTotal(articles.size());
        return page;
    }

    @Override
    public IPage<Article> getArticlesByTag(String tagName, Long current, Long size) {
        Page<Article> page = new Page<>(current, size);
        List<Article> articles = baseMapper.selectByTag(tagName);
        page.setRecords(articles);
        page.setTotal(articles.size());
        return page;
    }

    @Override
    public List<Article> getArchiveArticles(String year, String month) {
        LambdaQueryWrapper<Article> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Article::getStatus, 1)
               .eq(Article::getDeleted, 0);
        
        if (year != null) {
            wrapper.apply("YEAR(publish_time) = {0}", year);
        }
        if (month != null) {
            wrapper.apply("MONTH(publish_time) = {0}", month);
        }
        
        wrapper.orderByDesc(Article::getPublishTime);
        return this.list(wrapper);
    }
}

