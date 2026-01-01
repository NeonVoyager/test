package com.oneblog.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 文章实体
 */
@Data
@TableName("article")
public class Article {
    @TableId(type = IdType.AUTO)
    private Long id;
    
    private String title;           // 标题
    private String summary;         // 摘要
    private String content;         // 内容（Markdown）
    private String htmlContent;     // HTML内容
    private String coverImage;      // 封面图
    private Integer views;           // 浏览量
    private Integer likes;           // 点赞数
    private Integer comments;       // 评论数
    private Integer status;         // 状态：0-草稿，1-已发布
    private Integer isTop;          // 是否置顶：0-否，1-是
    private Integer isRecommend;    // 是否推荐：0-否，1-是
    private Long categoryId;        // 分类ID
    private String tags;            // 标签（逗号分隔）
    private String keywords;        // SEO关键词
    private LocalDateTime publishTime;  // 发布时间
    private LocalDateTime createTime;   // 创建时间
    private LocalDateTime updateTime;   // 更新时间
    private Integer deleted;        // 删除标记：0-未删除，1-已删除
}

