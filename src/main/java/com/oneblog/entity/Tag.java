package com.oneblog.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 标签实体
 */
@Data
@TableName("tag")
public class Tag {
    @TableId(type = IdType.AUTO)
    private Long id;
    
    private String name;            // 标签名称
    private String color;           // 标签颜色
    private Integer count;          // 使用次数
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private Integer deleted;
}

