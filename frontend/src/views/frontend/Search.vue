<template>
  <FrontendLayout>
    <div class="search-page">
      <div class="container">
        <el-row :gutter="20">
          <el-col :xs="24" :sm="24" :md="18" :lg="18">
            <el-card>
              <h2>搜索结果：{{ keyword }}</h2>
              <div v-loading="loading" class="article-list">
                <ArticleCard
                  v-for="article in articleList"
                  :key="article.id"
                  :article="article"
                />
                <el-empty v-if="!loading && articleList.length === 0" description="未找到相关文章" />
              </div>
              <el-pagination
                v-model:current-page="currentPage"
                v-model:page-size="pageSize"
                :total="total"
                layout="prev, pager, next"
                @current-change="loadArticles"
                class="pagination"
              />
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
import { ref, computed, onMounted, watch } from 'vue'
import { useRoute } from 'vue-router'
import FrontendLayout from '@/layouts/FrontendLayout.vue'
import ArticleCard from '@/components/frontend/ArticleCard.vue'
import Sidebar from '@/components/frontend/Sidebar.vue'
import { searchArticles } from '@/api/article'

const route = useRoute()
const keyword = computed(() => route.query.keyword || '')

const articleList = ref([])
const loading = ref(false)
const currentPage = ref(1)
const pageSize = ref(10)
const total = ref(0)

const loadArticles = async () => {
  if (!keyword.value) return
  
  loading.value = true
  try {
    const res = await searchArticles(keyword.value, {
      current: currentPage.value,
      size: pageSize.value
    })
    articleList.value = res.data.records || res.data
    total.value = res.data.total || res.data.length
  } catch (error) {
    console.error('搜索失败:', error)
  } finally {
    loading.value = false
  }
}

watch(() => route.query.keyword, () => {
  currentPage.value = 1
  loadArticles()
})

onMounted(() => {
  loadArticles()
})
</script>

<style scoped lang="scss">
.search-page {
  .container {
    max-width: 1200px;
    margin: 0 auto;
    padding: 0 20px;
  }

  .article-list {
    margin-top: 20px;
  }

  .pagination {
    display: flex;
    justify-content: center;
    margin-top: 30px;
  }
}
</style>

