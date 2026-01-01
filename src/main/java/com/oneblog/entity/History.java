package com.oneblog.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 浏览历史实体
 */
@Data
@TableName("history")
public class History {
    @TableId(type = IdType.AUTO)
    private Long id;
    
    private Long userId;            // 用户ID
    private Long articleId;         // 文章ID
    private LocalDateTime createTime;
}

