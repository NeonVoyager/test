# OneBlog 个人博客系统

一个功能完整的个人博客系统，包含前台展示和后台管理两个子系统。

## 技术栈

- **后端框架**: Spring Boot 2.7.14
- **数据库**: MySQL 8.0
- **ORM框架**: MyBatis Plus 3.5.3.1
- **权限管理**: Apache Shiro 1.12.0
- **缓存**: Redis
- **模板引擎**: Thymeleaf
- **Markdown解析**: CommonMark
- **其他**: Lombok, Hutool, FastJSON2

## 功能特性

### 前台展示子系统

#### 首页浏览
- ✅ 文章列表展示（支持分页）
- ✅ 显示文章摘要、封面图、发布时间、浏览量
- ✅ 轮播图/推荐（置顶或热门文章）
- ✅ 侧边栏（热门文章、最新评论、标签云、归档时间轴）

#### 文章详情
- ✅ Markdown渲染（代码高亮、公式支持）
- ✅ 目录导航（TOC）
- ✅ 点赞功能
- ✅ 分享功能（生成海报或链接）
- ✅ 评论功能（支持盖楼/回复）

#### 归档与分类
- ✅ 分类/标签墙（按分类或标签筛选文章）
- ✅ 时间归档（按年份/月份展示）

#### 搜索系统
- ✅ 全文搜索（关键词搜索文章标题和内容）

#### 其他页面
- ✅ 友情链接（展示友链列表，提供申请入口）
- ✅ 关于我（展示博主个人简介、联系方式）

#### 新增功能
- ✅ 收藏文章（已登录用户）
- ✅ 浏览历史记录及清除
- ✅ 邮箱订阅提醒（发布新文章时邮件通知）
- ✅ 推荐文章（根据文章标签推荐相似文章）

### 后台管理子系统

#### 仪表盘
- ✅ 数据统计（文章总数、评论数、访问量趋势图、最新日志）

#### 文章管理
- ✅ 文章发布（核心功能）
- ✅ 文章列表（CRUD操作、批量删除、状态管理）
- ✅ 分类/标签管理（创建、修改、删除）

#### 用户与权限
- ✅ 用户管理（管理后台用户、重置密码、分配角色）
- ✅ 角色/资源管理（配置角色、分配菜单访问权限）

#### 评论管理
- ✅ 评论审核（查看、回复、删除、屏蔽违规评论）

#### 系统配置
- ✅ 网站设置（SEO关键词、网站标题、页脚版权、ICP备案号）
- ✅ 菜单管理（自定义前台导航栏菜单项和排序）

#### 系统监控
- ✅ 运行时监控（系统内存、CPU使用率、数据库连接池状态）

#### 新增功能
- ✅ 处理举报（处理用户举报的评论）
- ✅ 关键字超链接（文章中对关键字设置超链接，支持跳转）

## 项目结构

```
oneblog/
├── src/
│   ├── main/
│   │   ├── java/com/oneblog/
│   │   │   ├── common/          # 通用类（Result, PageResult等）
│   │   │   ├── config/          # 配置类（Shiro, MyBatis Plus等）
│   │   │   ├── controller/      # 控制器
│   │   │   │   ├── admin/       # 后台管理控制器
│   │   │   │   └── ...          # 前台控制器
│   │   │   ├── entity/          # 实体类
│   │   │   ├── listener/        # 监听器
│   │   │   ├── mapper/          # Mapper接口
│   │   │   ├── service/         # Service接口和实现
│   │   │   └── util/            # 工具类
│   │   └── resources/
│   │       ├── mapper/          # MyBatis XML映射文件
│   │       ├── sql/             # SQL初始化脚本
│   │       └── application.yml  # 配置文件
│   └── test/
└── pom.xml
```

## 快速开始

### 1. 环境要求

- JDK 1.8+
- Maven 3.6+
- MySQL 8.0+
- Redis 5.0+

### 2. 数据库配置

1. 创建数据库：
```sql
CREATE DATABASE oneblog DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

2. 执行初始化脚本：
```bash
mysql -u root -p oneblog < src/main/resources/sql/init.sql
```

### 3. 配置文件

修改 `src/main/resources/application.yml` 中的数据库和Redis配置：

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/oneblog?useUnicode=true&characterEncoding=utf8&useSSL=false&serverTimezone=Asia/Shanghai
    username: your_username
    password: your_password
  
  redis:
    host: localhost
    port: 6379
    password: your_redis_password
```

### 4. 邮件配置

修改邮件配置（用于订阅提醒功能）：

```yaml
spring:
  mail:
    host: smtp.qq.com
    port: 587
    username: your-email@qq.com
    password: your-email-password
```

### 5. 运行项目

```bash
mvn clean install
mvn spring-boot:run
```

访问地址：
- 前台：http://localhost:8080
- 后台：http://localhost:8080/admin

默认管理员账号：
- 用户名：admin
- 密码：admin123（需要在数据库中修改为加密后的密码）

## API接口文档

### 前台API（/api）

#### 文章相关
- `GET /api/articles` - 获取文章列表（分页）
- `GET /api/article/{id}` - 获取文章详情
- `POST /api/article/{id}/like` - 点赞文章
- `GET /api/articles/hot` - 获取热门文章
- `GET /api/articles/top` - 获取置顶文章
- `GET /api/articles/search` - 搜索文章
- `GET /api/articles/category/{categoryId}` - 按分类获取文章
- `GET /api/articles/tag/{tagName}` - 按标签获取文章
- `GET /api/articles/archive` - 获取归档文章
- `GET /api/articles/recommend/{articleId}` - 获取推荐文章

#### 评论相关
- `GET /api/comments/article/{articleId}` - 获取文章评论
- `POST /api/comments` - 添加评论
- `POST /api/comments/reply/{parentId}` - 回复评论

#### 收藏相关
- `POST /api/favorites/{articleId}` - 收藏文章
- `DELETE /api/favorites/{articleId}` - 取消收藏
- `GET /api/favorites/check` - 检查是否已收藏
- `GET /api/favorites/user/{userId}` - 获取用户收藏列表

#### 历史记录
- `POST /api/history/{articleId}` - 添加浏览历史
- `GET /api/history/user/{userId}` - 获取浏览历史
- `DELETE /api/history/user/{userId}` - 清空浏览历史

#### 订阅相关
- `POST /api/subscribe` - 订阅邮箱提醒
- `POST /api/subscribe/verify` - 验证订阅

#### 其他
- `GET /api/sidebar` - 获取侧边栏数据
- `GET /api/categories` - 获取分类列表
- `GET /api/tags` - 获取标签列表
- `GET /api/friend-link/list` - 获取友情链接
- `POST /api/friend-link/apply` - 申请友情链接
- `POST /api/report/comment/{commentId}` - 举报评论

### 后台API（/admin）

#### 文章管理
- `GET /admin/article/list` - 获取文章列表
- `GET /admin/article/{id}` - 获取文章详情
- `POST /admin/article` - 保存/更新文章
- `DELETE /admin/article/{id}` - 删除文章
- `DELETE /admin/article/batch` - 批量删除文章

#### 评论管理
- `GET /admin/comment/list` - 获取评论列表
- `POST /admin/comment/audit/{id}` - 审核评论
- `DELETE /admin/comment/{id}` - 删除评论
- `POST /admin/comment/reply/{id}` - 回复评论

#### 分类/标签管理
- `GET /admin/category/list` - 获取分类列表
- `POST /admin/category` - 保存分类
- `DELETE /admin/category/{id}` - 删除分类
- `GET /admin/tag/list` - 获取标签列表
- `POST /admin/tag` - 保存标签
- `DELETE /admin/tag/{id}` - 删除标签

#### 用户管理
- `GET /admin/user/list` - 获取用户列表
- `POST /admin/user` - 保存用户
- `POST /admin/user/reset-password/{id}` - 重置密码
- `DELETE /admin/user/{id}` - 删除用户

#### 系统配置
- `GET /admin/system-config/all` - 获取所有配置
- `POST /admin/system-config` - 保存配置

#### 菜单管理
- `GET /admin/menu/list` - 获取菜单列表
- `POST /admin/menu` - 保存菜单
- `DELETE /admin/menu/{id}` - 删除菜单

#### 举报管理
- `GET /admin/report/list` - 获取举报列表
- `POST /admin/report/handle/{id}` - 处理举报

#### 关键字链接管理
- `GET /admin/keyword-link/list` - 获取关键字链接列表
- `POST /admin/keyword-link` - 保存关键字链接
- `DELETE /admin/keyword-link/{id}` - 删除关键字链接

#### 友情链接管理
- `GET /admin/friend-link/list` - 获取友情链接列表
- `POST /admin/friend-link/audit/{id}` - 审核友情链接
- `DELETE /admin/friend-link/{id}` - 删除友情链接

#### 仪表盘
- `GET /admin/dashboard/statistics` - 获取统计数据

## 开发说明

### 关键字链接功能

在后台管理中添加关键字和对应的链接地址，系统会自动在文章内容中将关键字替换为超链接。

### 邮件订阅功能

用户在前台订阅邮箱后，系统会发送验证邮件。验证通过后，当有新文章发布时，系统会自动发送邮件通知所有已验证的订阅用户。

### 推荐文章功能

系统会根据当前文章的标签，推荐具有相同标签的其他文章。

## 注意事项

1. **默认管理员密码**
   - 用户名：admin
   - 默认密码：admin123（需要在数据库中更新为加密后的密码）
   - 运行 `PasswordGenerator.main()` 生成加密密码，或使用以下SQL：
   ```sql
   UPDATE user SET password = '生成的加密密码' WHERE username = 'admin';
   ```

2. **邮件配置**
   - 需要根据实际使用的邮箱服务商进行配置
   - QQ邮箱需要使用授权码而非登录密码

3. **Shiro权限**
   - 已实现基本的认证授权功能
   - 可根据实际需求扩展权限控制

4. **文件上传**
   - 默认上传路径：`uploads/`
   - 确保目录有写权限
   - 可通过配置文件修改路径

5. **前端页面**
   - 已提供基础的前端模板（Thymeleaf）
   - 可根据需求进行样式和功能扩展

## 许可证

MIT License

## 贡献

欢迎提交Issue和Pull Request！

