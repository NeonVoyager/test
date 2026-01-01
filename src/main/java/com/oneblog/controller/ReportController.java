package com.oneblog.controller;

import com.oneblog.common.Result;
import com.oneblog.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 举报控制器（前台）
 */
@RestController
@RequestMapping("/api/report")
public class ReportController {

    @Autowired
    private ReportService reportService;

    /**
     * 举报评论
     */
    @PostMapping("/comment/{commentId}")
    public Result<Void> reportComment(
            @PathVariable Long commentId,
            @RequestParam(required = false) Long userId,
            @RequestParam String reason) {
        reportService.reportComment(commentId, userId, reason);
        return Result.success(null);
    }
}

