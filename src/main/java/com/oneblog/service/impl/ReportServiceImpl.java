package com.oneblog.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.oneblog.entity.Comment;
import com.oneblog.entity.Report;
import com.oneblog.mapper.ReportMapper;
import com.oneblog.service.CommentService;
import com.oneblog.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class ReportServiceImpl extends ServiceImpl<ReportMapper, Report> implements ReportService {

    @Autowired
    private CommentService commentService;

    @Override
    @Transactional
    public void reportComment(Long commentId, Long userId, String reason) {
        Report report = new Report();
        report.setCommentId(commentId);
        report.setUserId(userId);
        report.setReason(reason);
        report.setStatus(0); // 待处理
        report.setCreateTime(LocalDateTime.now());
        this.save(report);
        
        // 更新评论的举报状态
        Comment comment = commentService.getById(commentId);
        if (comment != null) {
            comment.setIsReported(1);
            commentService.updateById(comment);
        }
    }

    @Override
    @Transactional
    public void handleReport(Long reportId, Integer status, String handleResult) {
        Report report = this.getById(reportId);
        if (report != null) {
            report.setStatus(status);
            report.setHandleResult(handleResult);
            report.setUpdateTime(LocalDateTime.now());
            this.updateById(report);
        }
    }
}

