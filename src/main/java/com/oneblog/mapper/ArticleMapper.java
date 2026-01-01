package com.oneblog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.oneblog.entity.Article;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ArticleMapper extends BaseMapper<Article> {
    List<Article> selectHotArticles(@Param("limit") Integer limit);
    List<Article> selectLatestComments(@Param("limit") Integer limit);
    List<Article> selectByCategory(@Param("categoryId") Long categoryId);
    List<Article> selectByTag(@Param("tagName") String tagName);
    List<Article> searchArticles(@Param("keyword") String keyword);
    List<Article> selectRecommendArticles(@Param("articleId") Long articleId, @Param("tags") String tags, @Param("limit") Integer limit);
}

