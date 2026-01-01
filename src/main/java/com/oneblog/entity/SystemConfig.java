package com.oneblog.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 系统配置实体
 */
@Data
@TableName("system_config")
public class SystemConfig {
    @TableId(type = IdType.AUTO)
    private Long id;
    
    private String configKey;       // 配置键
    private String configValue;     // 配置值
    private String description;     // 描述
    private LocalDateTime updateTime;
}

