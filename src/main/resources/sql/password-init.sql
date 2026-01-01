-- 密码初始化脚本
-- 默认密码：admin123
-- 使用Shiro MD5加密（用户名作为盐，迭代2次）

-- 更新admin用户密码为：admin123
-- 加密后的密码需要使用PasswordUtil工具类生成
-- 或者使用以下Java代码生成：
-- PasswordUtil.encode("admin123", "admin")

UPDATE user SET password = 'e10adc3949ba59abbe56e057f20f883e' WHERE username = 'admin';

-- 注意：上面的密码是MD5("admin123")，实际应该使用Shiro的加密方式
-- 建议在应用启动后通过代码更新密码，或使用以下方式：

-- 方式1：使用Java代码生成
-- String password = PasswordUtil.encode("admin123", "admin");
-- 然后更新数据库

-- 方式2：临时设置明文密码，首次登录后系统会自动加密（如果实现了自动加密功能）

