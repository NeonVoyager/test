package com.oneblog.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 举报实体
 */
@Data
@TableName("report")
public class Report {
    @TableId(type = IdType.AUTO)
    private Long id;
    
    private Long commentId;         // 评论ID
    private Long userId;            // 举报人ID（可为空）
    private String reason;          // 举报原因
    private Integer status;         // 状态：0-待处理，1-已处理，2-已驳回
    private String handleResult;    // 处理结果
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}

