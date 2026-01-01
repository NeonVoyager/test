<template>
  <FrontendLayout>
    <div class="category-page">
      <div class="container">
        <el-row :gutter="20">
          <el-col :xs="24" :sm="24" :md="18" :lg="18">
            <el-card>
              <h2>分类：{{ categoryName }}</h2>
              <div v-loading="loading" class="article-list">
                <ArticleCard
                  v-for="article in articleList"
                  :key="article.id"
                  :article="article"
                />
                <el-empty v-if="!loading && articleList.length === 0" description="暂无文章" />
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
import { ref, computed, onMounted } from 'vue'
import { useRoute } from 'vue-router'
import FrontendLayout from '@/layouts/FrontendLayout.vue'
import ArticleCard from '@/components/frontend/ArticleCard.vue'
import Sidebar from '@/components/frontend/Sidebar.vue'
import { getArticlesByCategory } from '@/api/article'
import { getCategoryList } from '@/api/category'

const route = useRoute()
const categoryId = computed(() => route.params.id)

const articleList = ref([])
const categoryName = ref('')
const loading = ref(false)
const currentPage = ref(1)
const pageSize = ref(10)
const total = ref(0)

const loadCategory = async () => {
  try {
    const res = await getCategoryList()
    const category = res.data.find(c => c.id == categoryId.value)
    if (category) {
      categoryName.value = category.name
    }
  } catch (error) {
    console.error('加载分类失败:', error)
  }
}

const loadArticles = async () => {
  loading.value = true
  try {
    const res = await getArticlesByCategory(categoryId.value, {
      current: currentPage.value,
      size: pageSize.value
    })
    articleList.value = res.data.records || res.data
    total.value = res.data.total || res.data.length
  } catch (error) {
    console.error('加载文章失败:', error)
  } finally {
    loading.value = false
  }
}

onMounted(() => {
  loadCategory()
  loadArticles()
})
</script>

<style scoped lang="scss">
.category-page {
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

