<template>
  <div class="dashboard-page">
    <el-row :gutter="20">
      <el-col :xs="24" :sm="12" :md="6" :lg="6">
        <el-card class="stat-card">
          <div class="stat-content">
            <div class="stat-icon article">
              <el-icon><Document /></el-icon>
            </div>
            <div class="stat-info">
              <div class="stat-value">{{ statistics.articleCount || 0 }}</div>
              <div class="stat-label">文章总数</div>
            </div>
          </div>
        </el-card>
      </el-col>
      <el-col :xs="24" :sm="12" :md="6" :lg="6">
        <el-card class="stat-card">
          <div class="stat-content">
            <div class="stat-icon comment">
              <el-icon><ChatDotRound /></el-icon>
            </div>
            <div class="stat-info">
              <div class="stat-value">{{ statistics.commentCount || 0 }}</div>
              <div class="stat-label">评论总数</div>
            </div>
          </div>
        </el-card>
      </el-col>
      <el-col :xs="24" :sm="12" :md="6" :lg="6">
        <el-card class="stat-card">
          <div class="stat-content">
            <div class="stat-icon view">
              <el-icon><View /></el-icon>
            </div>
            <div class="stat-info">
              <div class="stat-value">{{ statistics.todayViews || 0 }}</div>
              <div class="stat-label">今日访问</div>
            </div>
          </div>
        </el-card>
      </el-col>
      <el-col :xs="24" :sm="12" :md="6" :lg="6">
        <el-card class="stat-card">
          <div class="stat-content">
            <div class="stat-icon user">
              <el-icon><User /></el-icon>
            </div>
            <div class="stat-info">
              <div class="stat-value">{{ statistics.userCount || 0 }}</div>
              <div class="stat-label">用户总数</div>
            </div>
          </div>
        </el-card>
      </el-col>
    </el-row>

    <el-row :gutter="20" style="margin-top: 20px">
      <el-col :span="24">
        <el-card>
          <template #header>
            <div class="card-header">最近文章</div>
          </template>
          <el-table :data="recentArticles" style="width: 100%">
            <el-table-column prop="title" label="标题" />
            <el-table-column prop="views" label="浏览量" width="100" />
            <el-table-column prop="comments" label="评论数" width="100" />
            <el-table-column prop="publishTime" label="发布时间" width="180">
              <template #default="{ row }">
                {{ formatDate(row.publishTime) }}
              </template>
            </el-table-column>
            <el-table-column label="操作" width="100">
              <template #default="{ row }">
                <el-button
                  link
                  type="primary"
                  @click="$router.push(`/admin/article/edit/${row.id}`)"
                >
                  编辑
                </el-button>
              </template>
            </el-table-column>
          </el-table>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { Document, ChatDotRound, View, User } from '@element-plus/icons-vue'
import { getStatistics } from '@/api/dashboard'
import { adminGetArticleList } from '@/api/article'
import { formatDate } from '@/utils/date'

const statistics = ref({})
const recentArticles = ref([])

const loadStatistics = async () => {
  try {
    const res = await getStatistics()
    statistics.value = res.data
  } catch (error) {
    console.error('加载统计数据失败:', error)
  }
}

const loadRecentArticles = async () => {
  try {
    const res = await adminGetArticleList({ current: 1, size: 10 })
    recentArticles.value = res.data.records
  } catch (error) {
    console.error('加载最近文章失败:', error)
  }
}

onMounted(() => {
  loadStatistics()
  loadRecentArticles()
})
</script>

<style scoped lang="scss">
.dashboard-page {
  .stat-card {
    .stat-content {
      display: flex;
      align-items: center;

      .stat-icon {
        width: 60px;
        height: 60px;
        border-radius: 8px;
        display: flex;
        align-items: center;
        justify-content: center;
        font-size: 30px;
        color: #fff;
        margin-right: 15px;

        &.article {
          background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
        }

        &.comment {
          background: linear-gradient(135deg, #f093fb 0%, #f5576c 100%);
        }

        &.view {
          background: linear-gradient(135deg, #4facfe 0%, #00f2fe 100%);
        }

        &.user {
          background: linear-gradient(135deg, #43e97b 0%, #38f9d7 100%);
        }
      }

      .stat-info {
        .stat-value {
          font-size: 28px;
          font-weight: bold;
          color: #333;
        }

        .stat-label {
          font-size: 14px;
          color: #999;
          margin-top: 5px;
        }
      }
    }
  }

  .card-header {
    font-weight: bold;
  }
}
</style>

