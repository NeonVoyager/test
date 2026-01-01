package com.oneblog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.oneblog.entity.Report;

public interface ReportService extends IService<Report> {
    void reportComment(Long commentId, Long userId, String reason);
    void handleReport(Long reportId, Integer status, String handleResult);
}

