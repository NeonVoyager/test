# 快速启动指南

## 前置准备

1. **安装JDK 1.8+**
   ```bash
   java -version
   ```

2. **安装Maven 3.6+**
   ```bash
   mvn -version
   ```

3. **安装MySQL 8.0+**
   ```bash
   mysql --version
   ```

4. **安装Redis 5.0+**
   ```bash
   redis-cli --version
   ```

## 数据库初始化

1. **创建数据库**
   ```sql
   CREATE DATABASE oneblog DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
   ```

2. **执行初始化脚本**
   ```bash
   mysql -u root -p oneblog < src/main/resources/sql/init.sql
   ```

   或者在MySQL客户端中执行：
   ```sql
   USE oneblog;
   SOURCE src/main/resources/sql/init.sql;
   ```

## 配置文件修改

编辑 `src/main/resources/application.yml`：

### 1. 数据库配置
```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/oneblog?useUnicode=true&characterEncoding=utf8&useSSL=false&serverTimezone=Asia/Shanghai&allowPublicKeyRetrieval=true
    username: root  # 修改为你的MySQL用户名
    password: root  # 修改为你的MySQL密码
```

### 2. Redis配置
```yaml
spring:
  redis:
    host: localhost
    port: 6379
    password:      # 如果有密码，填写Redis密码
```

### 3. 邮件配置（可选，用于订阅提醒功能）
```yaml
spring:
  mail:
    host: smtp.qq.com
    port: 587
    username: your-email@qq.com      # 修改为你的邮箱
    password: your-email-password      # 修改为你的邮箱授权码
```

## 启动项目

### 方式一：使用Maven命令
```bash
mvn clean install
mvn spring-boot:run
```

### 方式二：使用IDE
1. 导入项目到IDE（IntelliJ IDEA / Eclipse）
2. 等待Maven依赖下载完成
3. 运行 `OneBlogApplication` 主类

## 访问地址

- **前台首页**: http://localhost:8080
- **后台管理**: http://localhost:8080/admin
- **API接口**: http://localhost:8080/api

## 默认账号

- **用户名**: admin
- **密码**: admin123（需要在数据库中修改为BCrypt加密后的密码）

### 修改默认密码

1. 使用BCrypt工具生成加密密码，或使用以下代码：
```java
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
String encodedPassword = encoder.encode("your_new_password");
System.out.println(encodedPassword);
```

2. 更新数据库：
```sql
UPDATE user SET password = '生成的加密密码' WHERE username = 'admin';
```

## 功能测试

### 1. 测试文章API
```bash
# 获取文章列表
curl http://localhost:8080/api/articles?current=1&size=10

# 获取文章详情
curl http://localhost:8080/api/article/1
```

### 2. 测试后台API（需要登录）
```bash
# 获取文章列表
curl http://localhost:8080/admin/article/list?current=1&size=10
```

## 常见问题

### 1. 数据库连接失败
- 检查MySQL服务是否启动
- 检查数据库用户名密码是否正确
- 检查数据库是否已创建

### 2. Redis连接失败
- 检查Redis服务是否启动
- 检查Redis配置是否正确
- 如果不需要Redis，可以暂时注释相关配置

### 3. 端口被占用
- 修改 `application.yml` 中的 `server.port` 配置
- 或关闭占用8080端口的程序

### 4. 邮件发送失败
- 检查邮箱配置是否正确
- 检查是否开启了SMTP服务
- 使用授权码而非登录密码

## 下一步

1. 完善Shiro权限配置（实现Realm）
2. 开发前端页面（Vue.js或Thymeleaf模板）
3. 配置Nginx反向代理
4. 部署到生产环境

## 技术支持

如有问题，请提交Issue或联系开发者。

