package com.oneblog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.oneblog.entity.Favorite;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface FavoriteMapper extends BaseMapper<Favorite> {
    List<Favorite> selectByUserId(@Param("userId") Long userId);
    Favorite selectByUserIdAndArticleId(@Param("userId") Long userId, @Param("articleId") Long articleId);
}

