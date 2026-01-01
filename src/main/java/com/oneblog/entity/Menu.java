package com.oneblog.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 菜单实体
 */
@Data
@TableName("menu")
public class Menu {
    @TableId(type = IdType.AUTO)
    private Long id;
    
    private String name;            // 菜单名称
    private String url;             // 链接地址
    private String icon;            // 图标
    private Integer sort;           // 排序
    private Integer status;         // 状态：0-禁用，1-启用
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}

