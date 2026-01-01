<template>
  <FrontendLayout>
    <div class="home-page">
      <div class="container">
        <el-row :gutter="20">
          <!-- 左侧内容 -->
          <el-col :xs="24" :sm="24" :md="16" :lg="16">
            <!-- 轮播图 -->
            <el-carousel v-if="topArticles.length > 0" height="400px" class="carousel">
              <el-carousel-item v-for="article in topArticles" :key="article.id">
                <div class="carousel-item">
                  <img :src="article.coverImage || '/default-cover.jpg'" :alt="article.title" />
                  <div class="carousel-content">
                    <h2>{{ article.title }}</h2>
                    <p>{{ article.summary }}</p>
                    <el-button type="primary" @click="$router.push(`/article/${article.id}`)">
                      阅读全文
                    </el-button>
                  </div>
                </div>
              </el-carousel-item>
            </el-carousel>

            <!-- 文章列表 -->
            <div class="article-list">
              <ArticleCard
                v-for="article in articleList"
                :key="article.id"
                :article="article"
              />
            </div>

            <!-- 分页 -->
            <el-pagination
              v-model:current-page="currentPage"
              v-model:page-size="pageSize"
              :total="total"
              layout="prev, pager, next"
              @current-change="loadArticles"
              class="pagination"
            />
          </el-col>

          <!-- 右侧边栏 -->
          <el-col :xs="24" :sm="24" :md="8" :lg="8">
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
import ArticleCard from '@/components/frontend/ArticleCard.vue'
import Sidebar from '@/components/frontend/Sidebar.vue'
import { getArticleList, getTopArticles } from '@/api/article'

const articleList = ref([])
const topArticles = ref([])
const currentPage = ref(1)
const pageSize = ref(10)
const total = ref(0)

const loadArticles = async () => {
  try {
    const res = await getArticleList({
      current: currentPage.value,
      size: pageSize.value
    })
    articleList.value = res.data.records
    total.value = res.data.total
  } catch (error) {
    console.error('加载文章失败:', error)
  }
}

const loadTopArticles = async () => {
  try {
    const res = await getTopArticles(5)
    topArticles.value = res.data
  } catch (error) {
    console.error('加载置顶文章失败:', error)
  }
}

onMounted(() => {
  loadTopArticles()
  loadArticles()
})
</script>

<style scoped lang="scss">
.home-page {
  .container {
    max-width: 1200px;
    margin: 0 auto;
    padding: 0 20px;
  }

  .carousel {
    margin-bottom: 30px;
    border-radius: 8px;
    overflow: hidden;

    .carousel-item {
      position: relative;
      height: 400px;

      img {
        width: 100%;
        height: 100%;
        object-fit: cover;
      }

      .carousel-content {
        position: absolute;
        bottom: 0;
        left: 0;
        right: 0;
        background: linear-gradient(transparent, rgba(0, 0, 0, 0.7));
        color: #fff;
        padding: 40px 30px 30px;

        h2 {
          margin: 0 0 10px;
          font-size: 24px;
        }

        p {
          margin: 0 0 15px;
          opacity: 0.9;
        }
      }
    }
  }

  .article-list {
    margin-bottom: 30px;
  }

  .pagination {
    display: flex;
    justify-content: center;
    margin-top: 30px;
  }
}
</style>

