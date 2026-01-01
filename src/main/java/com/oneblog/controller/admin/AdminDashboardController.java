package com.oneblog.controller.admin;

import com.oneblog.common.Result;
import com.oneblog.entity.Article;
import com.oneblog.entity.Comment;
import com.oneblog.service.ArticleService;
import com.oneblog.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * 后台仪表盘控制器
 */
@RestController
@RequestMapping("/admin/dashboard")
public class AdminDashboardController {

    @Autowired
    private ArticleService articleService;

    @Autowired
    private CommentService commentService;

    /**
     * 获取统计数据
     */
    @GetMapping("/statistics")
    public Result<Map<String, Object>> getStatistics() {
        Map<String, Object> data = new HashMap<>();
        
        // 文章总数
        long articleCount = articleService.count();
        data.put("articleCount", articleCount);
        
        // 评论总数
        long commentCount = commentService.count();
        data.put("commentCount", commentCount);
        
        // 今日访问量（需要实现访问量统计）
        data.put("todayViews", 0);
        
        // 待审核评论数
        // data.put("pendingComments", commentService.countPendingComments());
        
        return Result.success(data);
    }
}

