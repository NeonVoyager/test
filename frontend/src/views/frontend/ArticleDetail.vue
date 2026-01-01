<template>
  <FrontendLayout>
    <div class="article-detail-page">
      <div class="container">
        <el-row :gutter="20">
          <!-- 文章内容 -->
          <el-col :xs="24" :sm="24" :md="18" :lg="18">
            <el-card v-loading="loading" class="article-card">
              <template v-if="article">
                <h1 class="article-title">{{ article.title }}</h1>
                <div class="article-meta">
                  <span><el-icon><Calendar /></el-icon> {{ formatDate(article.publishTime) }}</span>
                  <span><el-icon><View /></el-icon> {{ article.views || 0 }} 次浏览</span>
                  <span><el-icon><ChatDotRound /></el-icon> {{ article.comments || 0 }} 条评论</span>
                </div>
                <div
                  class="article-content"
                  v-html="renderedContent"
                ></div>
                <div class="article-actions">
                  <el-button
                    :type="isLiked ? 'danger' : 'primary'"
                    @click="handleLike"
                    :loading="likeLoading"
                  >
                    <el-icon><Star /></el-icon>
                    {{ article.likes || 0 }}
                  </el-button>
                  <el-button @click="handleShare">
                    <el-icon><Share /></el-icon>
                    分享
                  </el-button>
                  <el-button
                    v-if="userId"
                    :type="isFavorited ? 'warning' : 'default'"
                    @click="handleFavorite"
                    :loading="favoriteLoading"
                  >
                    <el-icon><Collection /></el-icon>
                    {{ isFavorited ? '已收藏' : '收藏' }}
                  </el-button>
                </div>

                <!-- 推荐文章 -->
                <div v-if="recommendArticles.length > 0" class="recommend-articles">
                  <h3>推荐文章</h3>
                  <el-row :gutter="20">
                    <el-col
                      v-for="item in recommendArticles"
                      :key="item.id"
                      :span="12"
                    >
                      <el-card shadow="hover" @click="$router.push(`/article/${item.id}`)">
                        <h4>{{ item.title }}</h4>
                      </el-card>
                    </el-col>
                  </el-row>
                </div>

                <!-- 评论区域 -->
                <div class="comment-section">
                  <h3>评论 ({{ comments.length }})</h3>
                  
                  <!-- 评论表单 -->
                  <el-card class="comment-form-card">
                    <el-form :model="commentForm" label-width="80px">
                      <el-form-item label="昵称" required>
                        <el-input v-model="commentForm.nickname" placeholder="请输入昵称" />
                      </el-form-item>
                      <el-form-item label="邮箱">
                        <el-input v-model="commentForm.email" placeholder="请输入邮箱（可选）" />
                      </el-form-item>
                      <el-form-item label="评论" required>
                        <el-input
                          v-model="commentForm.content"
                          type="textarea"
                          :rows="4"
                          placeholder="写下你的评论..."
                        />
                      </el-form-item>
                      <el-form-item>
                        <el-button type="primary" @click="submitComment" :loading="commentLoading">
                          提交评论
                        </el-button>
                      </el-form-item>
                    </el-form>
                  </el-card>

                  <!-- 评论列表 -->
                  <div class="comment-list">
                    <CommentItem
                      v-for="comment in comments"
                      :key="comment.id"
                      :comment="comment"
                      @reply="handleReply"
                    />
                  </div>
                </div>
              </template>
            </el-card>
          </el-col>

          <!-- 侧边栏 -->
          <el-col :xs="24" :sm="24" :md="6" :lg="6">
            <div class="toc-container">
              <el-card>
                <template #header>
                  <div class="card-header">目录</div>
                </template>
                <div v-if="toc.length > 0" class="toc">
                  <div
                    v-for="item in toc"
                    :key="item.id"
                    :class="['toc-item', `toc-level-${item.level}`]"
                    @click="scrollToHeading(item.id)"
                  >
                    {{ item.text }}
                  </div>
                </div>
                <div v-else class="empty">暂无目录</div>
              </el-card>
            </div>
          </el-col>
        </el-row>
      </div>
    </div>
  </FrontendLayout>
</template>

<script setup>
import { ref, computed, onMounted, watch } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import FrontendLayout from '@/layouts/FrontendLayout.vue'
import CommentItem from '@/components/frontend/CommentItem.vue'
import {
  Calendar,
  View,
  ChatDotRound,
  Star,
  Share,
  Collection
} from '@element-plus/icons-vue'
import { getArticleDetail, likeArticle, getRecommendArticles } from '@/api/article'
import { getCommentsByArticleId, addComment } from '@/api/comment'
import { isFavorited, addFavorite, removeFavorite } from '@/api/favorite'
import { addHistory } from '@/api/history'
import { renderMarkdown, generateTOC } from '@/utils/markdown'
import { formatDate } from '@/utils/date'
import { ElMessage } from 'element-plus'

const route = useRoute()
const router = useRouter()

const articleId = computed(() => route.params.id)
const userId = ref(localStorage.getItem('userId') || null)

const loading = ref(false)
const article = ref(null)
const renderedContent = ref('')
const toc = ref([])
const comments = ref([])
const recommendArticles = ref([])

const isLiked = ref(false)
const likeLoading = ref(false)
const isFavoritedStatus = ref(false)
const favoriteLoading = ref(false)

const commentForm = ref({
  nickname: '',
  email: '',
  content: ''
})
const commentLoading = ref(false)

const loadArticle = async () => {
  loading.value = true
  try {
    const res = await getArticleDetail(articleId.value)
    article.value = res.data
    
    // 渲染Markdown
    if (article.value.content) {
      renderedContent.value = renderMarkdown(article.value.content)
      // 生成目录
      setTimeout(() => {
        toc.value = generateTOC(renderedContent.value)
      }, 100)
    } else if (article.value.htmlContent) {
      renderedContent.value = article.value.htmlContent
      setTimeout(() => {
        toc.value = generateTOC(renderedContent.value)
      }, 100)
    }
    
    // 添加浏览历史
    if (userId.value) {
      addHistory(articleId.value, userId.value)
    }
  } catch (error) {
    ElMessage.error('加载文章失败')
  } finally {
    loading.value = false
  }
}

const loadComments = async () => {
  try {
    const res = await getCommentsByArticleId(articleId.value)
    comments.value = res.data
  } catch (error) {
    console.error('加载评论失败:', error)
  }
}

const loadRecommendArticles = async () => {
  try {
    const res = await getRecommendArticles(articleId.value, 4)
    recommendArticles.value = res.data
  } catch (error) {
    console.error('加载推荐文章失败:', error)
  }
}

const checkFavorite = async () => {
  if (!userId.value) return
  try {
    const res = await isFavorited(userId.value, articleId.value)
    isFavoritedStatus.value = res.data
  } catch (error) {
    console.error('检查收藏状态失败:', error)
  }
}

const handleLike = async () => {
  likeLoading.value = true
  try {
    await likeArticle(articleId.value)
    article.value.likes = (article.value.likes || 0) + 1
    isLiked.value = true
    ElMessage.success('点赞成功')
  } catch (error) {
    ElMessage.error('点赞失败')
  } finally {
    likeLoading.value = false
  }
}

const handleFavorite = async () => {
  if (!userId.value) {
    ElMessage.warning('请先登录')
    return
  }
  
  favoriteLoading.value = true
  try {
    if (isFavoritedStatus.value) {
      await removeFavorite(articleId.value, userId.value)
      isFavoritedStatus.value = false
      ElMessage.success('取消收藏成功')
    } else {
      await addFavorite(articleId.value, userId.value)
      isFavoritedStatus.value = true
      ElMessage.success('收藏成功')
    }
  } catch (error) {
    ElMessage.error('操作失败')
  } finally {
    favoriteLoading.value = false
  }
}

const handleShare = () => {
  const url = window.location.href
  if (navigator.share) {
    navigator.share({
      title: article.value.title,
      text: article.value.summary,
      url: url
    })
  } else {
    navigator.clipboard.writeText(url)
    ElMessage.success('链接已复制到剪贴板')
  }
}

const submitComment = async () => {
  if (!commentForm.value.nickname || !commentForm.value.content) {
    ElMessage.warning('请填写昵称和评论内容')
    return
  }
  
  commentLoading.value = true
  try {
    await addComment({
      articleId: articleId.value,
      nickname: commentForm.value.nickname,
      email: commentForm.value.email,
      content: commentForm.value.content
    })
    ElMessage.success('评论提交成功，等待审核')
    commentForm.value = {
      nickname: '',
      email: '',
      content: ''
    }
    loadComments()
  } catch (error) {
    ElMessage.error('提交评论失败')
  } finally {
    commentLoading.value = false
  }
}

const handleReply = (commentId) => {
  // 处理回复
  ElMessage.info('回复功能开发中')
}

const scrollToHeading = (id) => {
  const element = document.getElementById(id)
  if (element) {
    element.scrollIntoView({ behavior: 'smooth', block: 'start' })
  }
}

watch(() => route.params.id, () => {
  loadArticle()
  loadComments()
  loadRecommendArticles()
  checkFavorite()
})

onMounted(() => {
  loadArticle()
  loadComments()
  loadRecommendArticles()
  checkFavorite()
})
</script>

<style scoped lang="scss">
.article-detail-page {
  .container {
    max-width: 1200px;
    margin: 0 auto;
    padding: 0 20px;
  }

  .article-card {
    .article-title {
      font-size: 32px;
      font-weight: bold;
      margin: 0 0 20px;
      color: #333;
    }

    .article-meta {
      display: flex;
      gap: 20px;
      color: #999;
      margin-bottom: 30px;
      padding-bottom: 20px;
      border-bottom: 1px solid #f0f0f0;

      span {
        display: flex;
        align-items: center;
        gap: 5px;
      }
    }

    .article-content {
      line-height: 1.8;
      font-size: 16px;
      margin-bottom: 30px;

      :deep(h1),
      :deep(h2),
      :deep(h3),
      :deep(h4),
      :deep(h5),
      :deep(h6) {
        margin-top: 30px;
        margin-bottom: 15px;
        font-weight: bold;
      }

      :deep(p) {
        margin-bottom: 15px;
      }

      :deep(img) {
        max-width: 100%;
        border-radius: 8px;
        margin: 20px 0;
      }

      :deep(pre) {
        background: #f4f4f4;
        padding: 15px;
        border-radius: 5px;
        overflow-x: auto;
        margin: 20px 0;
      }

      :deep(code) {
        background: #f4f4f4;
        padding: 2px 6px;
        border-radius: 3px;
      }

      :deep(pre code) {
        background: transparent;
        padding: 0;
      }

      :deep(blockquote) {
        border-left: 4px solid #409eff;
        padding-left: 15px;
        margin: 20px 0;
        color: #666;
      }

      :deep(ul),
      :deep(ol) {
        margin: 15px 0;
        padding-left: 30px;
      }

      :deep(li) {
        margin: 5px 0;
      }
    }

    .article-actions {
      display: flex;
      gap: 10px;
      margin-bottom: 40px;
      padding-top: 20px;
      border-top: 1px solid #f0f0f0;
    }

    .recommend-articles {
      margin-top: 40px;
      padding-top: 30px;
      border-top: 1px solid #f0f0f0;

      h3 {
        margin-bottom: 20px;
      }
    }

    .comment-section {
      margin-top: 40px;
      padding-top: 30px;
      border-top: 1px solid #f0f0f0;

      h3 {
        margin-bottom: 20px;
      }

      .comment-form-card {
        margin-bottom: 30px;
      }
    }
  }

  .toc-container {
    position: sticky;
    top: 20px;

    .toc {
      .toc-item {
        padding: 8px 0;
        cursor: pointer;
        color: #666;
        transition: color 0.3s;

        &:hover {
          color: #409eff;
        }
      }

      .toc-level-1 {
        font-weight: bold;
        font-size: 16px;
      }

      .toc-level-2 {
        padding-left: 15px;
        font-size: 14px;
      }

      .toc-level-3 {
        padding-left: 30px;
        font-size: 13px;
      }
    }
  }
}
</style>

