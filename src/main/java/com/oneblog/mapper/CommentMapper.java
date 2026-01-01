package com.oneblog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.oneblog.entity.Comment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CommentMapper extends BaseMapper<Comment> {
    List<Comment> selectByArticleId(@Param("articleId") Long articleId);
    List<Comment> selectLatestComments(@Param("limit") Integer limit);
}

