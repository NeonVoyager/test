package com.oneblog.controller;

import com.oneblog.common.Result;
import com.oneblog.entity.History;
import com.oneblog.service.HistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 历史记录控制器
 */
@RestController
@RequestMapping("/api/history")
public class HistoryController {

    @Autowired
    private HistoryService historyService;

    /**
     * 添加浏览历史
     */
    @PostMapping("/{articleId}")
    public Result<Void> addHistory(@PathVariable Long articleId, @RequestParam Long userId) {
        historyService.addHistory(userId, articleId);
        return Result.success(null);
    }

    /**
     * 获取用户浏览历史
     */
    @GetMapping("/user/{userId}")
    public Result<List<History>> getUserHistory(
            @PathVariable Long userId,
            @RequestParam(defaultValue = "20") Integer limit) {
        List<History> history = historyService.getUserHistory(userId, limit);
        return Result.success(history);
    }

    /**
     * 清空浏览历史
     */
    @DeleteMapping("/user/{userId}")
    public Result<Void> clearHistory(@PathVariable Long userId) {
        historyService.clearHistory(userId);
        return Result.success(null);
    }
}

