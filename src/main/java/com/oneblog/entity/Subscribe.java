package com.oneblog.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 订阅实体（邮箱提醒）
 */
@Data
@TableName("subscribe")
public class Subscribe {
    @TableId(type = IdType.AUTO)
    private Long id;
    
    private String email;           // 邮箱
    private Integer status;         // 状态：0-未验证，1-已验证
    private String verifyCode;      // 验证码
    private LocalDateTime createTime;
    private LocalDateTime verifyTime;
}

