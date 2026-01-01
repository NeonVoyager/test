package com.oneblog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.oneblog.entity.Favorite;

import java.util.List;

public interface FavoriteService extends IService<Favorite> {
    void addFavorite(Long userId, Long articleId);
    void removeFavorite(Long userId, Long articleId);
    boolean isFavorited(Long userId, Long articleId);
    List<Favorite> getUserFavorites(Long userId);
}

