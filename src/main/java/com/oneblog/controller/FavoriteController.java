package com.oneblog.controller;

import com.oneblog.common.Result;
import com.oneblog.entity.Favorite;
import com.oneblog.service.FavoriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 收藏控制器
 */
@RestController
@RequestMapping("/api/favorites")
public class FavoriteController {

    @Autowired
    private FavoriteService favoriteService;

    /**
     * 收藏文章
     */
    @PostMapping("/{articleId}")
    public Result<Void> addFavorite(@PathVariable Long articleId, @RequestParam Long userId) {
        favoriteService.addFavorite(userId, articleId);
        return Result.success(null);
    }

    /**
     * 取消收藏
     */
    @DeleteMapping("/{articleId}")
    public Result<Void> removeFavorite(@PathVariable Long articleId, @RequestParam Long userId) {
        favoriteService.removeFavorite(userId, articleId);
        return Result.success(null);
    }

    /**
     * 检查是否已收藏
     */
    @GetMapping("/check")
    public Result<Boolean> isFavorited(@RequestParam Long userId, @RequestParam Long articleId) {
        boolean favorited = favoriteService.isFavorited(userId, articleId);
        return Result.success(favorited);
    }

    /**
     * 获取用户收藏列表
     */
    @GetMapping("/user/{userId}")
    public Result<List<Favorite>> getUserFavorites(@PathVariable Long userId) {
        List<Favorite> favorites = favoriteService.getUserFavorites(userId);
        return Result.success(favorites);
    }
}

