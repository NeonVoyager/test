package com.oneblog.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.oneblog.entity.Favorite;
import com.oneblog.mapper.FavoriteMapper;
import com.oneblog.service.FavoriteService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class FavoriteServiceImpl extends ServiceImpl<FavoriteMapper, Favorite> implements FavoriteService {

    @Override
    @Transactional
    public void addFavorite(Long userId, Long articleId) {
        Favorite favorite = baseMapper.selectByUserIdAndArticleId(userId, articleId);
        if (favorite == null) {
            favorite = new Favorite();
            favorite.setUserId(userId);
            favorite.setArticleId(articleId);
            favorite.setCreateTime(LocalDateTime.now());
            this.save(favorite);
        }
    }

    @Override
    @Transactional
    public void removeFavorite(Long userId, Long articleId) {
        Favorite favorite = baseMapper.selectByUserIdAndArticleId(userId, articleId);
        if (favorite != null) {
            this.removeById(favorite.getId());
        }
    }

    @Override
    public boolean isFavorited(Long userId, Long articleId) {
        Favorite favorite = baseMapper.selectByUserIdAndArticleId(userId, articleId);
        return favorite != null;
    }

    @Override
    public List<Favorite> getUserFavorites(Long userId) {
        return baseMapper.selectByUserId(userId);
    }
}

