<template>
  <div class="frontend-layout">
    <!-- 导航栏 -->
    <el-header class="header">
      <div class="container">
        <div class="header-content">
          <router-link to="/" class="logo">
            <h1>OneBlog</h1>
          </router-link>
          <el-menu
            :default-active="activeMenu"
            mode="horizontal"
            router
            class="nav-menu"
          >
            <el-menu-item index="/">首页</el-menu-item>
            <el-menu-item index="/archive">归档</el-menu-item>
            <el-menu-item index="/about">关于</el-menu-item>
            <el-menu-item index="/friend-link">友链</el-menu-item>
          </el-menu>
          <div class="header-actions">
            <el-input
              v-model="searchKeyword"
              placeholder="搜索文章..."
              class="search-input"
              @keyup.enter="handleSearch"
            >
              <template #prefix>
                <el-icon><Search /></el-icon>
              </template>
            </el-input>
          </div>
        </div>
      </div>
    </el-header>

    <!-- 主要内容 -->
    <el-main class="main-content">
      <router-view />
    </el-main>

    <!-- 页脚 -->
    <el-footer class="footer">
      <div class="container">
        <p>&copy; 2024 OneBlog. All rights reserved.</p>
      </div>
    </el-footer>
  </div>
</template>

<script setup>
import { ref, computed } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { Search } from '@element-plus/icons-vue'

const route = useRoute()
const router = useRouter()
const searchKeyword = ref('')

const activeMenu = computed(() => route.path)

const handleSearch = () => {
  if (searchKeyword.value.trim()) {
    router.push({
      path: '/search',
      query: { keyword: searchKeyword.value }
    })
  }
}
</script>

<style scoped lang="scss">
.frontend-layout {
  min-height: 100vh;
  display: flex;
  flex-direction: column;
}

.header {
  background: #fff;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
  padding: 0;
  height: 70px !important;
  line-height: 70px;

  .container {
    max-width: 1200px;
    margin: 0 auto;
    padding: 0 20px;
  }

  .header-content {
    display: flex;
    align-items: center;
    justify-content: space-between;
  }

  .logo {
    font-size: 24px;
    font-weight: bold;
    color: #409eff;
    margin-right: 40px;

    h1 {
      margin: 0;
      font-size: 24px;
    }
  }

  .nav-menu {
    flex: 1;
    border-bottom: none;
  }

  .search-input {
    width: 300px;
  }
}

.main-content {
  flex: 1;
  padding: 20px 0;
  background: #f5f5f5;

  .container {
    max-width: 1200px;
    margin: 0 auto;
    padding: 0 20px;
  }
}

.footer {
  background: #2c3e50;
  color: #fff;
  text-align: center;
  padding: 20px 0;
  margin-top: auto;
}
</style>

