-- 创建数据库
CREATE DATABASE IF NOT EXISTS oneblog DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE oneblog;

-- 文章表
CREATE TABLE IF NOT EXISTS `article` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `title` VARCHAR(255) NOT NULL COMMENT '标题',
  `summary` TEXT COMMENT '摘要',
  `content` LONGTEXT COMMENT '内容（Markdown）',
  `html_content` LONGTEXT COMMENT 'HTML内容',
  `cover_image` VARCHAR(500) COMMENT '封面图',
  `views` INT(11) DEFAULT 0 COMMENT '浏览量',
  `likes` INT(11) DEFAULT 0 COMMENT '点赞数',
  `comments` INT(11) DEFAULT 0 COMMENT '评论数',
  `status` TINYINT(1) DEFAULT 0 COMMENT '状态：0-草稿，1-已发布',
  `is_top` TINYINT(1) DEFAULT 0 COMMENT '是否置顶：0-否，1-是',
  `is_recommend` TINYINT(1) DEFAULT 0 COMMENT '是否推荐：0-否，1-是',
  `category_id` BIGINT(20) COMMENT '分类ID',
  `tags` VARCHAR(500) COMMENT '标签（逗号分隔）',
  `keywords` VARCHAR(255) COMMENT 'SEO关键词',
  `publish_time` DATETIME COMMENT '发布时间',
  `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` TINYINT(1) DEFAULT 0 COMMENT '删除标记：0-未删除，1-已删除',
  PRIMARY KEY (`id`),
  KEY `idx_category_id` (`category_id`),
  KEY `idx_status` (`status`),
  KEY `idx_publish_time` (`publish_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='文章表';

-- 分类表
CREATE TABLE IF NOT EXISTS `category` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `name` VARCHAR(100) NOT NULL COMMENT '分类名称',
  `description` VARCHAR(500) COMMENT '描述',
  `sort` INT(11) DEFAULT 0 COMMENT '排序',
  `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` TINYINT(1) DEFAULT 0 COMMENT '删除标记',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='分类表';

-- 标签表
CREATE TABLE IF NOT EXISTS `tag` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `name` VARCHAR(100) NOT NULL COMMENT '标签名称',
  `color` VARCHAR(20) COMMENT '标签颜色',
  `count` INT(11) DEFAULT 0 COMMENT '使用次数',
  `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` TINYINT(1) DEFAULT 0 COMMENT '删除标记',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='标签表';

-- 评论表
CREATE TABLE IF NOT EXISTS `comment` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `article_id` BIGINT(20) NOT NULL COMMENT '文章ID',
  `user_id` BIGINT(20) COMMENT '用户ID（游客可为空）',
  `nickname` VARCHAR(100) NOT NULL COMMENT '昵称',
  `email` VARCHAR(100) COMMENT '邮箱',
  `content` TEXT NOT NULL COMMENT '评论内容',
  `parent_id` BIGINT(20) COMMENT '父评论ID（回复）',
  `ip` VARCHAR(50) COMMENT 'IP地址',
  `status` TINYINT(1) DEFAULT 0 COMMENT '状态：0-待审核，1-已通过，2-已屏蔽',
  `is_reported` TINYINT(1) DEFAULT 0 COMMENT '是否被举报：0-否，1-是',
  `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` TINYINT(1) DEFAULT 0 COMMENT '删除标记',
  PRIMARY KEY (`id`),
  KEY `idx_article_id` (`article_id`),
  KEY `idx_parent_id` (`parent_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='评论表';

-- 用户表
CREATE TABLE IF NOT EXISTS `user` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `username` VARCHAR(50) NOT NULL UNIQUE COMMENT '用户名',
  `password` VARCHAR(255) NOT NULL COMMENT '密码（加密）',
  `nickname` VARCHAR(100) COMMENT '昵称',
  `email` VARCHAR(100) COMMENT '邮箱',
  `avatar` VARCHAR(500) COMMENT '头像',
  `role` VARCHAR(20) DEFAULT 'editor' COMMENT '角色：admin-管理员，editor-编辑',
  `status` TINYINT(1) DEFAULT 1 COMMENT '状态：0-禁用，1-启用',
  `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` TINYINT(1) DEFAULT 0 COMMENT '删除标记',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户表';

-- 收藏表
CREATE TABLE IF NOT EXISTS `favorite` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `user_id` BIGINT(20) NOT NULL COMMENT '用户ID',
  `article_id` BIGINT(20) NOT NULL COMMENT '文章ID',
  `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_user_article` (`user_id`, `article_id`),
  KEY `idx_user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='收藏表';

-- 浏览历史表
CREATE TABLE IF NOT EXISTS `history` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `user_id` BIGINT(20) NOT NULL COMMENT '用户ID',
  `article_id` BIGINT(20) NOT NULL COMMENT '文章ID',
  `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `idx_user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='浏览历史表';

-- 关键字链接表
CREATE TABLE IF NOT EXISTS `keyword_link` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `keyword` VARCHAR(100) NOT NULL COMMENT '关键字',
  `link_url` VARCHAR(500) NOT NULL COMMENT '链接地址',
  `status` TINYINT(1) DEFAULT 1 COMMENT '状态：0-禁用，1-启用',
  `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='关键字链接表';

-- 举报表
CREATE TABLE IF NOT EXISTS `report` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `comment_id` BIGINT(20) NOT NULL COMMENT '评论ID',
  `user_id` BIGINT(20) COMMENT '举报人ID（可为空）',
  `reason` VARCHAR(500) COMMENT '举报原因',
  `status` TINYINT(1) DEFAULT 0 COMMENT '状态：0-待处理，1-已处理，2-已驳回',
  `handle_result` VARCHAR(500) COMMENT '处理结果',
  `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_comment_id` (`comment_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='举报表';

-- 友情链接表
CREATE TABLE IF NOT EXISTS `friend_link` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `name` VARCHAR(100) NOT NULL COMMENT '网站名称',
  `url` VARCHAR(500) NOT NULL COMMENT '链接地址',
  `logo` VARCHAR(500) COMMENT 'Logo',
  `description` VARCHAR(500) COMMENT '描述',
  `status` TINYINT(1) DEFAULT 0 COMMENT '状态：0-待审核，1-已通过',
  `sort` INT(11) DEFAULT 0 COMMENT '排序',
  `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` TINYINT(1) DEFAULT 0 COMMENT '删除标记',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='友情链接表';

-- 系统配置表
CREATE TABLE IF NOT EXISTS `system_config` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `config_key` VARCHAR(100) NOT NULL UNIQUE COMMENT '配置键',
  `config_value` TEXT COMMENT '配置值',
  `description` VARCHAR(500) COMMENT '描述',
  `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统配置表';

-- 菜单表
CREATE TABLE IF NOT EXISTS `menu` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `name` VARCHAR(100) NOT NULL COMMENT '菜单名称',
  `url` VARCHAR(500) COMMENT '链接地址',
  `icon` VARCHAR(100) COMMENT '图标',
  `sort` INT(11) DEFAULT 0 COMMENT '排序',
  `status` TINYINT(1) DEFAULT 1 COMMENT '状态：0-禁用，1-启用',
  `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='菜单表';

-- 订阅表
CREATE TABLE IF NOT EXISTS `subscribe` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `email` VARCHAR(100) NOT NULL UNIQUE COMMENT '邮箱',
  `status` TINYINT(1) DEFAULT 0 COMMENT '状态：0-未验证，1-已验证',
  `verify_code` VARCHAR(20) COMMENT '验证码',
  `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `verify_time` DATETIME COMMENT '验证时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='订阅表';

-- 插入默认管理员用户（密码：admin123，需要在实际使用时修改）
INSERT INTO `user` (`username`, `password`, `nickname`, `role`, `status`) 
VALUES ('admin', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iwK8pJwC', '管理员', 'admin', 1);

-- 插入默认系统配置
INSERT INTO `system_config` (`config_key`, `config_value`, `description`) VALUES
('site_title', 'OneBlog', '网站标题'),
('site_keywords', '博客,个人博客', 'SEO关键词'),
('site_description', '个人博客系统', '网站描述'),
('site_footer', '© 2024 OneBlog', '页脚信息'),
('icp_number', '', 'ICP备案号');

