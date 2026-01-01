package com.oneblog.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 评论实体
 */
@Data
@TableName("comment")
public class Comment {
    @TableId(type = IdType.AUTO)
    private Long id;
    
    private Long articleId;         // 文章ID
    private Long userId;            // 用户ID（游客可为空）
    private String nickname;        // 昵称
    private String email;           // 邮箱
    private String content;         // 评论内容
    private Long parentId;          // 父评论ID（回复）
    private String ip;              // IP地址
    private Integer status;         // 状态：0-待审核，1-已通过，2-已屏蔽
    private Integer isReported;     // 是否被举报：0-否，1-是
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private Integer deleted;
}

