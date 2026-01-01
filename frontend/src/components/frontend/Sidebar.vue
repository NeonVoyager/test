<template>
  <div class="sidebar">
    <!-- 搜索框 -->
    <el-card class="sidebar-card" shadow="never">
      <el-input
        v-model="searchKeyword"
        placeholder="搜索文章..."
        @keyup.enter="handleSearch"
      >
        <template #prefix>
          <el-icon><Search /></el-icon>
        </template>
      </el-input>
    </el-card>

    <!-- 热门文章 -->
    <el-card class="sidebar-card" shadow="never">
      <template #header>
        <div class="card-header">
          <el-icon><Fire /></el-icon>
          <span>热门文章</span>
        </div>
      </template>
      <div v-loading="hotLoading">
        <div
          v-for="article in hotArticles"
          :key="article.id"
          class="hot-article-item"
          @click="$router.push(`/article/${article.id}`)"
        >
          {{ article.title }}
        </div>
        <div v-if="hotArticles.length === 0" class="empty">暂无数据</div>
      </div>
    </el-card>

    <!-- 最新评论 -->
    <el-card class="sidebar-card" shadow="never">
      <template #header>
        <div class="card-header">
          <el-icon><ChatDotRound /></el-icon>
          <span>最新评论</span>
        </div>
      </template>
      <div v-loading="commentLoading">
        <div
          v-for="comment in latestComments"
          :key="comment.id"
          class="comment-item"
          @click="$router.push(`/article/${comment.articleId}`)"
        >
          <div class="comment-author">{{ comment.nickname }}</div>
          <div class="comment-content">{{ comment.content }}</div>
        </div>
        <div v-if="latestComments.length === 0" class="empty">暂无数据</div>
      </div>
    </el-card>

    <!-- 标签云 -->
    <el-card class="sidebar-card" shadow="never">
      <template #header>
        <div class="card-header">
          <el-icon><CollectionTag /></el-icon>
          <span>标签云</span>
        </div>
      </template>
      <div v-loading="tagLoading" class="tag-cloud">
        <el-tag
          v-for="tag in tags"
          :key="tag.id"
          :type="getTagType(tag)"
          class="tag-item"
          @click="$router.push(`/tag/${tag.name}`)"
        >
          {{ tag.name }}
        </el-tag>
        <div v-if="tags.length === 0" class="empty">暂无标签</div>
      </div>
    </el-card>

    <!-- 分类 -->
    <el-card class="sidebar-card" shadow="never">
      <template #header>
        <div class="card-header">
          <el-icon><FolderOpened /></el-icon>
          <span>分类</span>
        </div>
      </template>
      <div v-loading="categoryLoading">
        <div
          v-for="category in categories"
          :key="category.id"
          class="category-item"
          @click="$router.push(`/category/${category.id}`)"
        >
          {{ category.name }}
        </div>
        <div v-if="categories.length === 0" class="empty">暂无分类</div>
      </div>
    </el-card>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import {
  Search,
  Fire,
  ChatDotRound,
  CollectionTag,
  FolderOpened
} from '@element-plus/icons-vue'
import { getHotArticles } from '@/api/article'
import { getTagList } from '@/api/tag'
import { getCategoryList } from '@/api/category'
import request from '@/api/index'

const router = useRouter()
const searchKeyword = ref('')

const hotArticles = ref([])
const latestComments = ref([])
const tags = ref([])
const categories = ref([])

const hotLoading = ref(false)
const commentLoading = ref(false)
const tagLoading = ref(false)
const categoryLoading = ref(false)

const handleSearch = () => {
  if (searchKeyword.value.trim()) {
    router.push({
      path: '/search',
      query: { keyword: searchKeyword.value }
    })
  }
}

const loadHotArticles = async () => {
  hotLoading.value = true
  try {
    const res = await getHotArticles(10)
    hotArticles.value = res.data
  } catch (error) {
    console.error('加载热门文章失败:', error)
  } finally {
    hotLoading.value = false
  }
}

const loadLatestComments = async () => {
  commentLoading.value = true
  try {
    const res = await request({
      url: '/api/comments/latest',
      method: 'get',
      params: { limit: 10 }
    })
    latestComments.value = res.data
  } catch (error) {
    console.error('加载最新评论失败:', error)
  } finally {
    commentLoading.value = false
  }
}

const loadTags = async () => {
  tagLoading.value = true
  try {
    const res = await getTagList()
    tags.value = res.data
  } catch (error) {
    console.error('加载标签失败:', error)
  } finally {
    tagLoading.value = false
  }
}

const loadCategories = async () => {
  categoryLoading.value = true
  try {
    const res = await getCategoryList()
    categories.value = res.data
  } catch (error) {
    console.error('加载分类失败:', error)
  } finally {
    categoryLoading.value = false
  }
}

const getTagType = (tag) => {
  const types = ['', 'success', 'info', 'warning', 'danger']
  return types[tag.id % types.length]
}

onMounted(() => {
  loadHotArticles()
  loadLatestComments()
  loadTags()
  loadCategories()
})
</script>

<style scoped lang="scss">
.sidebar {
  .sidebar-card {
    margin-bottom: 20px;

    .card-header {
      display: flex;
      align-items: center;
      gap: 8px;
      font-weight: bold;
    }

    .hot-article-item,
    .category-item {
      padding: 10px;
      cursor: pointer;
      border-bottom: 1px solid #f0f0f0;
      transition: background 0.3s;

      &:hover {
        background: #f5f5f5;
      }

      &:last-child {
        border-bottom: none;
      }
    }

    .comment-item {
      padding: 10px;
      cursor: pointer;
      border-bottom: 1px solid #f0f0f0;

      &:last-child {
        border-bottom: none;
      }

      .comment-author {
        font-weight: bold;
        color: #409eff;
        margin-bottom: 5px;
      }

      .comment-content {
        color: #666;
        font-size: 14px;
        overflow: hidden;
        text-overflow: ellipsis;
        white-space: nowrap;
      }
    }

    .tag-cloud {
      .tag-item {
        margin: 5px;
        cursor: pointer;
        transition: transform 0.3s;

        &:hover {
          transform: scale(1.1);
        }
      }
    }

    .empty {
      text-align: center;
      color: #999;
      padding: 20px;
    }
  }
}
</style>

