package com.oneblog.controller.admin;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.oneblog.common.Result;
import com.oneblog.entity.Report;
import com.oneblog.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 后台举报管理控制器
 */
@RestController
@RequestMapping("/admin/report")
public class AdminReportController {

    @Autowired
    private ReportService reportService;

    /**
     * 获取举报列表
     */
    @GetMapping("/list")
    public Result<IPage<Report>> getReportList(
            @RequestParam(defaultValue = "1") Long current,
            @RequestParam(defaultValue = "10") Long size) {
        Page<Report> page = new Page<>(current, size);
        IPage<Report> result = reportService.page(page);
        return Result.success(result);
    }

    /**
     * 处理举报
     */
    @PostMapping("/handle/{id}")
    public Result<Void> handleReport(
            @PathVariable Long id,
            @RequestParam Integer status,
            @RequestParam String handleResult) {
        reportService.handleReport(id, status, handleResult);
        return Result.success(null);
    }
}

