package com.oneblog.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 用户实体
 */
@Data
@TableName("user")
public class User {
    @TableId(type = IdType.AUTO)
    private Long id;
    
    private String username;        // 用户名
    private String password;        // 密码（加密）
    private String nickname;        // 昵称
    private String email;           // 邮箱
    private String avatar;          // 头像
    private String role;            // 角色：admin-管理员，editor-编辑
    private Integer status;         // 状态：0-禁用，1-启用
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private Integer deleted;
}

