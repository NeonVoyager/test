package com.oneblog.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 关键字链接实体
 */
@Data
@TableName("keyword_link")
public class KeywordLink {
    @TableId(type = IdType.AUTO)
    private Long id;
    
    private String keyword;         // 关键字
    private String linkUrl;         // 链接地址
    private Integer status;         // 状态：0-禁用，1-启用
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}

