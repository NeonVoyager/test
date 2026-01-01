# OneBlog 前端项目

基于 Vue 3 + Element Plus 的前端项目，与后端完全分离。

## 技术栈

- **Vue 3** - 渐进式 JavaScript 框架
- **Vue Router** - 官方路由管理器
- **Pinia** - 状态管理
- **Element Plus** - Vue 3 UI 组件库
- **Axios** - HTTP 客户端
- **Vite** - 下一代前端构建工具
- **Marked** - Markdown 解析器
- **Highlight.js** - 代码高亮

## 项目结构

```
frontend/
├── src/
│   ├── api/              # API 接口
│   ├── components/       # 组件
│   │   └── frontend/    # 前台组件
│   ├── layouts/          # 布局组件
│   ├── router/           # 路由配置
│   ├── stores/            # Pinia 状态管理
│   ├── styles/            # 样式文件
│   ├── utils/             # 工具函数
│   ├── views/             # 页面组件
│   │   ├── frontend/     # 前台页面
│   │   └── admin/        # 后台页面
│   ├── App.vue            # 根组件
│   └── main.js            # 入口文件
├── index.html
├── package.json
├── vite.config.js
└── README.md
```

## 快速开始

### 安装依赖

```bash
cd frontend
npm install
```

### 开发

```bash
npm run dev
```

访问 http://localhost:3000

### 构建

```bash
npm run build
```

### 预览构建结果

```bash
npm run preview
```

## 功能特性

### 前台功能
- ✅ 首页（文章列表、轮播图、侧边栏）
- ✅ 文章详情（Markdown渲染、目录、评论）
- ✅ 分类/标签页面
- ✅ 归档页面
- ✅ 搜索功能
- ✅ 关于页面
- ✅ 友情链接
- ✅ 收藏功能
- ✅ 浏览历史
- ✅ 邮箱订阅

### 后台功能
- ✅ 仪表盘
- ✅ 文章管理（CRUD）
- ✅ 评论管理
- ✅ 分类/标签管理
- ✅ 用户管理
- ✅ 系统配置
- ✅ 菜单管理
- ✅ 举报处理
- ✅ 关键字链接管理
- ✅ 友情链接管理
- ✅ 系统监控

## 配置说明

### 代理配置

在 `vite.config.js` 中配置了代理：

```javascript
server: {
  proxy: {
    '/api': {
      target: 'http://localhost:8080',
      changeOrigin: true
    }
  }
}
```

### 环境变量

可以创建 `.env` 文件配置环境变量：

```env
VITE_API_BASE_URL=http://localhost:8080
```

## 注意事项

1. 确保后端服务已启动（默认端口 8080）
2. 登录后 token 会存储在 localStorage
3. 需要登录的功能会检查 token 是否存在

