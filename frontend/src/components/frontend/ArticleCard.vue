<template>
  <el-card class="article-card" shadow="hover">
    <el-row :gutter="20">
      <el-col :span="coverImage ? 8 : 24">
        <div class="article-content">
          <h3 class="article-title" @click="goToDetail">
            {{ article.title }}
          </h3>
          <p class="article-summary">{{ article.summary || '暂无摘要' }}</p>
          <div class="article-meta">
            <span><el-icon><Calendar /></el-icon> {{ formatDateOnly(article.publishTime) }}</span>
            <span><el-icon><View /></el-icon> {{ article.views || 0 }}</span>
            <span><el-icon><ChatDotRound /></el-icon> {{ article.comments || 0 }}</span>
            <span><el-icon><Star /></el-icon> {{ article.likes || 0 }}</span>
          </div>
        </div>
      </el-col>
      <el-col v-if="coverImage" :span="16">
        <div class="article-cover" @click="goToDetail">
          <img :src="coverImage" :alt="article.title" />
        </div>
      </el-col>
    </el-row>
  </el-card>
</template>

<script setup>
import { computed } from 'vue'
import { useRouter } from 'vue-router'
import { Calendar, View, ChatDotRound, Star } from '@element-plus/icons-vue'
import { formatDateOnly } from '@/utils/date'

const props = defineProps({
  article: {
    type: Object,
    required: true
  }
})

const router = useRouter()
const coverImage = computed(() => props.article.coverImage)

const goToDetail = () => {
  router.push(`/article/${props.article.id}`)
}
</script>

<style scoped lang="scss">
.article-card {
  margin-bottom: 20px;
  cursor: pointer;
  transition: transform 0.3s;

  &:hover {
    transform: translateY(-5px);
  }

  .article-content {
    .article-title {
      font-size: 20px;
      font-weight: bold;
      margin: 0 0 15px;
      color: #333;
      cursor: pointer;
      transition: color 0.3s;

      &:hover {
        color: #409eff;
      }
    }

    .article-summary {
      color: #666;
      line-height: 1.6;
      margin: 0 0 15px;
      display: -webkit-box;
      -webkit-line-clamp: 3;
      -webkit-box-orient: vertical;
      overflow: hidden;
    }

    .article-meta {
      display: flex;
      gap: 20px;
      color: #999;
      font-size: 14px;

      span {
        display: flex;
        align-items: center;
        gap: 5px;
      }
    }
  }

  .article-cover {
    height: 150px;
    overflow: hidden;
    border-radius: 4px;
    cursor: pointer;

    img {
      width: 100%;
      height: 100%;
      object-fit: cover;
      transition: transform 0.3s;
    }

    &:hover img {
      transform: scale(1.1);
    }
  }
}
</style>

