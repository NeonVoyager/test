package com.oneblog.util;

/**
 * 密码生成工具（用于初始化管理员密码）
 * 运行main方法可以生成加密后的密码
 */
public class PasswordGenerator {
    
    public static void main(String[] args) {
        // 生成admin用户的密码（密码：admin123，盐：admin）
        String password = "admin123";
        String salt = "admin";
        String encodedPassword = PasswordUtil.encode(password, salt);
        
        System.out.println("========================================");
        System.out.println("密码生成工具");
        System.out.println("========================================");
        System.out.println("原始密码: " + password);
        System.out.println("盐值: " + salt);
        System.out.println("加密后密码: " + encodedPassword);
        System.out.println("========================================");
        System.out.println("SQL更新语句:");
        System.out.println("UPDATE user SET password = '" + encodedPassword + "' WHERE username = 'admin';");
        System.out.println("========================================");
    }
}

