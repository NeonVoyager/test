import { createRouter, createWebHistory } from 'vue-router'
import NProgress from 'nprogress'

// 前台路由
const frontendRoutes = [
  {
    path: '/',
    name: 'Home',
    component: () => import('@/views/frontend/Home.vue'),
    meta: { title: '首页' }
  },
  {
    path: '/article/:id',
    name: 'ArticleDetail',
    component: () => import('@/views/frontend/ArticleDetail.vue'),
    meta: { title: '文章详情' }
  },
  {
    path: '/category/:id',
    name: 'Category',
    component: () => import('@/views/frontend/Category.vue'),
    meta: { title: '分类' }
  },
  {
    path: '/tag/:name',
    name: 'Tag',
    component: () => import('@/views/frontend/Tag.vue'),
    meta: { title: '标签' }
  },
  {
    path: '/archive',
    name: 'Archive',
    component: () => import('@/views/frontend/Archive.vue'),
    meta: { title: '归档' }
  },
  {
    path: '/search',
    name: 'Search',
    component: () => import('@/views/frontend/Search.vue'),
    meta: { title: '搜索' }
  },
  {
    path: '/about',
    name: 'About',
    component: () => import('@/views/frontend/About.vue'),
    meta: { title: '关于' }
  },
  {
    path: '/friend-link',
    name: 'FriendLink',
    component: () => import('@/views/frontend/FriendLink.vue'),
    meta: { title: '友情链接' }
  }
]

// 后台路由
const adminRoutes = [
  {
    path: '/admin',
    component: () => import('@/layouts/AdminLayout.vue'),
    redirect: '/admin/dashboard',
    meta: { requiresAuth: true },
    children: [
      {
        path: 'dashboard',
        name: 'AdminDashboard',
        component: () => import('@/views/admin/Dashboard.vue'),
        meta: { title: '仪表盘', requiresAuth: true }
      },
      {
        path: 'article',
        name: 'AdminArticle',
        component: () => import('@/views/admin/Article.vue'),
        meta: { title: '文章管理', requiresAuth: true }
      },
      {
        path: 'article/edit/:id?',
        name: 'AdminArticleEdit',
        component: () => import('@/views/admin/ArticleEdit.vue'),
        meta: { title: '编辑文章', requiresAuth: true }
      },
      {
        path: 'comment',
        name: 'AdminComment',
        component: () => import('@/views/admin/Comment.vue'),
        meta: { title: '评论管理', requiresAuth: true }
      },
      {
        path: 'category',
        name: 'AdminCategory',
        component: () => import('@/views/admin/Category.vue'),
        meta: { title: '分类管理', requiresAuth: true }
      },
      {
        path: 'tag',
        name: 'AdminTag',
        component: () => import('@/views/admin/Tag.vue'),
        meta: { title: '标签管理', requiresAuth: true }
      },
      {
        path: 'user',
        name: 'AdminUser',
        component: () => import('@/views/admin/User.vue'),
        meta: { title: '用户管理', requiresAuth: true }
      },
      {
        path: 'system-config',
        name: 'AdminSystemConfig',
        component: () => import('@/views/admin/SystemConfig.vue'),
        meta: { title: '系统配置', requiresAuth: true }
      },
      {
        path: 'menu',
        name: 'AdminMenu',
        component: () => import('@/views/admin/Menu.vue'),
        meta: { title: '菜单管理', requiresAuth: true }
      },
      {
        path: 'report',
        name: 'AdminReport',
        component: () => import('@/views/admin/Report.vue'),
        meta: { title: '举报处理', requiresAuth: true }
      },
      {
        path: 'keyword-link',
        name: 'AdminKeywordLink',
        component: () => import('@/views/admin/KeywordLink.vue'),
        meta: { title: '关键字链接', requiresAuth: true }
      },
      {
        path: 'friend-link',
        name: 'AdminFriendLink',
        component: () => import('@/views/admin/FriendLink.vue'),
        meta: { title: '友情链接管理', requiresAuth: true }
      },
      {
        path: 'monitor',
        name: 'AdminMonitor',
        component: () => import('@/views/admin/Monitor.vue'),
        meta: { title: '系统监控', requiresAuth: true }
      }
    ]
  },
  {
    path: '/admin/login',
    name: 'AdminLogin',
    component: () => import('@/views/admin/Login.vue'),
    meta: { title: '后台登录' }
  }
]

const routes = [...frontendRoutes, ...adminRoutes]

const router = createRouter({
  history: createWebHistory(),
  routes
})

// 路由守卫
router.beforeEach((to, from, next) => {
  NProgress.start()
  
  // 设置页面标题
  document.title = to.meta.title ? `${to.meta.title} - OneBlog` : 'OneBlog'
  
  // 检查是否需要登录
  if (to.meta.requiresAuth) {
    const token = localStorage.getItem('token')
    if (!token) {
      next('/admin/login')
      return
    }
  }
  
  // 如果已登录，访问登录页则跳转到后台
  if (to.path === '/admin/login') {
    const token = localStorage.getItem('token')
    if (token) {
      next('/admin/dashboard')
      return
    }
  }
  
  next()
})

router.afterEach(() => {
  NProgress.done()
})

export default router

