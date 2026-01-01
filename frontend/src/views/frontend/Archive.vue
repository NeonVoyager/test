<template>
  <FrontendLayout>
    <div class="archive-page">
      <div class="container">
        <el-row :gutter="20">
          <el-col :xs="24" :sm="24" :md="18" :lg="18">
            <el-card>
              <h2>文章归档</h2>
              <div v-loading="loading">
                <div v-for="(articles, yearMonth) in archiveData" :key="yearMonth" class="archive-item">
                  <h3 class="archive-title">{{ yearMonth }}</h3>
                  <div class="article-list">
                    <div
                      v-for="article in articles"
                      :key="article.id"
                      class="archive-article-item"
                      @click="$router.push(`/article/${article.id}`)"
                    >
                      <span class="article-date">{{ formatDateOnly(article.publishTime) }}</span>
                      <span class="article-title">{{ article.title }}</span>
                    </div>
                  </div>
                </div>
                <el-empty v-if="!loading && Object.keys(archiveData).length === 0" description="暂无文章" />
              </div>
            </el-card>
          </el-col>
          <el-col :xs="24" :sm="24" :md="6" :lg="6">
            <Sidebar />
          </el-col>
        </el-row>
      </div>
    </div>
  </FrontendLayout>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import FrontendLayout from '@/layouts/FrontendLayout.vue'
import Sidebar from '@/components/frontend/Sidebar.vue'
import { getArchiveArticles } from '@/api/article'
import { formatDateOnly } from '@/utils/date'
import dayjs from 'dayjs'

const archiveData = ref({})
const loading = ref(false)

const loadArchive = async () => {
  loading.value = true
  try {
    const res = await getArchiveArticles()
    const articles = res.data
    
    // 按年月分组
    const grouped = {}
    articles.forEach(article => {
      const date = dayjs(article.publishTime)
      const key = date.format('YYYY年MM月')
      if (!grouped[key]) {
        grouped[key] = []
      }
      grouped[key].push(article)
    })
    
    archiveData.value = grouped
  } catch (error) {
    console.error('加载归档失败:', error)
  } finally {
    loading.value = false
  }
}

onMounted(() => {
  loadArchive()
})
</script>

<style scoped lang="scss">
.archive-page {
  .container {
    max-width: 1200px;
    margin: 0 auto;
    padding: 0 20px;
  }

  .archive-item {
    margin-bottom: 30px;

    .archive-title {
      font-size: 20px;
      font-weight: bold;
      margin-bottom: 15px;
      padding-bottom: 10px;
      border-bottom: 2px solid #409eff;
    }

    .article-list {
      .archive-article-item {
        display: flex;
        padding: 12px 0;
        border-bottom: 1px solid #f0f0f0;
        cursor: pointer;
        transition: background 0.3s;

        &:hover {
          background: #f5f5f5;
          padding-left: 10px;
        }

        .article-date {
          width: 120px;
          color: #999;
          font-size: 14px;
        }

        .article-title {
          flex: 1;
          color: #333;
          transition: color 0.3s;

          &:hover {
            color: #409eff;
          }
        }
      }
    }
  }
}
</style>

