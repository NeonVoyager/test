package com.oneblog.util;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

/**
 * 密码工具类
 */
public class PasswordUtil {

    private static final String ALGORITHM_NAME = "MD5";
    private static final int HASH_ITERATIONS = 2;

    /**
     * 加密密码
     */
    public static String encode(String password, String salt) {
        return new SimpleHash(ALGORITHM_NAME, password, ByteSource.Util.bytes(salt), HASH_ITERATIONS).toHex();
    }

    /**
     * 验证密码
     */
    public static boolean matches(String rawPassword, String encodedPassword, String salt) {
        String hash = encode(rawPassword, salt);
        return hash.equals(encodedPassword);
    }

    /**
     * 生成盐值（使用用户名作为盐）
     */
    public static String generateSalt(String username) {
        return username;
    }
}

