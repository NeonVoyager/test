package com.oneblog.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 友情链接实体
 */
@Data
@TableName("friend_link")
public class FriendLink {
    @TableId(type = IdType.AUTO)
    private Long id;
    
    private String name;            // 网站名称
    private String url;             // 链接地址
    private String logo;            // Logo
    private String description;     // 描述
    private Integer status;         // 状态：0-待审核，1-已通过
    private Integer sort;           // 排序
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private Integer deleted;
}

