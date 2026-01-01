package com.oneblog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.oneblog.entity.History;

import java.util.List;

public interface HistoryService extends IService<History> {
    void addHistory(Long userId, Long articleId);
    List<History> getUserHistory(Long userId, Integer limit);
    void clearHistory(Long userId);
}

