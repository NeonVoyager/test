package com.oneblog.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.oneblog.entity.History;
import com.oneblog.mapper.HistoryMapper;
import com.oneblog.service.HistoryService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class HistoryServiceImpl extends ServiceImpl<HistoryMapper, History> implements HistoryService {

    @Override
    @Transactional
    public void addHistory(Long userId, Long articleId) {
        History history = new History();
        history.setUserId(userId);
        history.setArticleId(articleId);
        history.setCreateTime(LocalDateTime.now());
        this.save(history);
    }

    @Override
    public List<History> getUserHistory(Long userId, Integer limit) {
        return baseMapper.selectByUserId(userId, limit);
    }

    @Override
    @Transactional
    public void clearHistory(Long userId) {
        baseMapper.deleteByUserId(userId);
    }
}

