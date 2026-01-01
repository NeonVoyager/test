<template>
  <el-container class="admin-layout">
    <el-aside width="200px" class="sidebar">
      <div class="logo">
        <h2>OneBlog</h2>
      </div>
      <el-menu
        :default-active="activeMenu"
        router
        background-color="#304156"
        text-color="#bfcbd9"
        active-text-color="#409eff"
      >
        <el-menu-item index="/admin/dashboard">
          <el-icon><Odometer /></el-icon>
          <span>仪表盘</span>
        </el-menu-item>
        <el-menu-item index="/admin/article">
          <el-icon><Document /></el-icon>
          <span>文章管理</span>
        </el-menu-item>
        <el-menu-item index="/admin/comment">
          <el-icon><ChatDotRound /></el-icon>
          <span>评论管理</span>
        </el-menu-item>
        <el-menu-item index="/admin/category">
          <el-icon><FolderOpened /></el-icon>
          <span>分类管理</span>
        </el-menu-item>
        <el-menu-item index="/admin/tag">
          <el-icon><CollectionTag /></el-icon>
          <span>标签管理</span>
        </el-menu-item>
        <el-menu-item index="/admin/user">
          <el-icon><User /></el-icon>
          <span>用户管理</span>
        </el-menu-item>
        <el-menu-item index="/admin/friend-link">
          <el-icon><Link /></el-icon>
          <span>友情链接</span>
        </el-menu-item>
        <el-menu-item index="/admin/report">
          <el-icon><Warning /></el-icon>
          <span>举报处理</span>
        </el-menu-item>
        <el-menu-item index="/admin/keyword-link">
          <el-icon><Key /></el-icon>
          <span>关键字链接</span>
        </el-menu-item>
        <el-menu-item index="/admin/system-config">
          <el-icon><Setting /></el-icon>
          <span>系统配置</span>
        </el-menu-item>
        <el-menu-item index="/admin/menu">
          <el-icon><Menu /></el-icon>
          <span>菜单管理</span>
        </el-menu-item>
        <el-menu-item index="/admin/monitor">
          <el-icon><Monitor /></el-icon>
          <span>系统监控</span>
        </el-menu-item>
      </el-menu>
    </el-aside>
    <el-container>
      <el-header class="header">
        <div class="header-left">
          <el-breadcrumb separator="/">
            <el-breadcrumb-item :to="{ path: '/admin/dashboard' }">首页</el-breadcrumb-item>
            <el-breadcrumb-item>{{ currentTitle }}</el-breadcrumb-item>
          </el-breadcrumb>
        </div>
        <div class="header-right">
          <el-dropdown @command="handleCommand">
            <span class="user-info">
              <el-icon><User /></el-icon>
              {{ userStore.username }}
              <el-icon><ArrowDown /></el-icon>
            </span>
            <template #dropdown>
              <el-dropdown-menu>
                <el-dropdown-item command="logout">退出登录</el-dropdown-item>
              </el-dropdown-menu>
            </template>
          </el-dropdown>
        </div>
      </el-header>
      <el-main class="main-content">
        <router-view />
      </el-main>
    </el-container>
  </el-container>
</template>

<script setup>
import { computed } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { useUserStore } from '@/stores/user'
import {
  Odometer,
  Document,
  ChatDotRound,
  FolderOpened,
  CollectionTag,
  User,
  Link,
  Warning,
  Key,
  Setting,
  Menu,
  Monitor,
  ArrowDown
} from '@element-plus/icons-vue'
import { ElMessageBox } from 'element-plus'

const route = useRoute()
const router = useRouter()
const userStore = useUserStore()

const activeMenu = computed(() => route.path)
const currentTitle = computed(() => route.meta.title || '管理')

const handleCommand = async (command) => {
  if (command === 'logout') {
    try {
      await ElMessageBox.confirm('确定要退出登录吗？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      })
      await userStore.logout()
      router.push('/admin/login')
    } catch (error) {
      // 用户取消
    }
  }
}
</script>

<style scoped lang="scss">
.admin-layout {
  height: 100vh;

  .sidebar {
    background-color: #304156;
    overflow-y: auto;

    .logo {
      height: 60px;
      display: flex;
      align-items: center;
      justify-content: center;
      background-color: #2b3a4a;

      h2 {
        margin: 0;
        color: #fff;
        font-size: 20px;
      }
    }

    .el-menu {
      border-right: none;
    }
  }

  .header {
    background: #fff;
    box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
    display: flex;
    align-items: center;
    justify-content: space-between;
    padding: 0 20px;

    .header-right {
      .user-info {
        display: flex;
        align-items: center;
        gap: 5px;
        cursor: pointer;
        color: #333;
      }
    }
  }

  .main-content {
    background: #f5f5f5;
    padding: 20px;
  }
}
</style>

