<template>
  <div class="article-page">
    <el-card>
      <template #header>
        <div class="card-header">
          <span>文章管理</span>
          <el-button type="primary" @click="$router.push('/admin/article/edit')">
            <el-icon><Plus /></el-icon>
            新增文章
          </el-button>
        </div>
      </template>

      <!-- 搜索栏 -->
      <el-form :inline="true" class="search-form">
        <el-form-item label="标题">
          <el-input v-model="searchForm.title" placeholder="请输入标题" clearable />
        </el-form-item>
        <el-form-item label="状态">
          <el-select v-model="searchForm.status" placeholder="请选择状态" clearable>
            <el-option label="草稿" :value="0" />
            <el-option label="已发布" :value="1" />
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="loadArticles">查询</el-button>
          <el-button @click="resetSearch">重置</el-button>
        </el-form-item>
      </el-form>

      <!-- 文章列表 -->
      <el-table
        v-loading="loading"
        :data="articleList"
        style="width: 100%"
        @selection-change="handleSelectionChange"
      >
        <el-table-column type="selection" width="55" />
        <el-table-column prop="title" label="标题" min-width="200" />
        <el-table-column prop="views" label="浏览量" width="100" />
        <el-table-column prop="likes" label="点赞数" width="100" />
        <el-table-column prop="comments" label="评论数" width="100" />
        <el-table-column prop="status" label="状态" width="100">
          <template #default="{ row }">
            <el-tag :type="row.status === 1 ? 'success' : 'info'">
              {{ row.status === 1 ? '已发布' : '草稿' }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="publishTime" label="发布时间" width="180">
          <template #default="{ row }">
            {{ formatDate(row.publishTime) }}
          </template>
        </el-table-column>
        <el-table-column label="操作" width="200" fixed="right">
          <template #default="{ row }">
            <el-button
              link
              type="primary"
              @click="$router.push(`/admin/article/edit/${row.id}`)"
            >
              编辑
            </el-button>
            <el-button link type="danger" @click="handleDelete(row.id)">
              删除
            </el-button>
          </template>
        </el-table-column>
      </el-table>

      <!-- 批量操作 -->
      <div v-if="selectedArticles.length > 0" class="batch-actions">
        <el-button type="danger" @click="handleBatchDelete">
          批量删除 ({{ selectedArticles.length }})
        </el-button>
      </div>

      <!-- 分页 -->
      <el-pagination
        v-model:current-page="currentPage"
        v-model:page-size="pageSize"
        :total="total"
        layout="total, prev, pager, next"
        @current-change="loadArticles"
        style="margin-top: 20px; justify-content: flex-end"
      />
    </el-card>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { Plus } from '@element-plus/icons-vue'
import { ElMessageBox, ElMessage } from 'element-plus'
import { adminGetArticleList, adminDeleteArticle, adminBatchDeleteArticles } from '@/api/article'
import { formatDate } from '@/utils/date'

const loading = ref(false)
const articleList = ref([])
const selectedArticles = ref([])
const currentPage = ref(1)
const pageSize = ref(10)
const total = ref(0)

const searchForm = reactive({
  title: '',
  status: null
})

const loadArticles = async () => {
  loading.value = true
  try {
    const params = {
      current: currentPage.value,
      size: pageSize.value,
      ...searchForm
    }
    const res = await adminGetArticleList(params)
    articleList.value = res.data.records
    total.value = res.data.total
  } catch (error) {
    ElMessage.error('加载文章失败')
  } finally {
    loading.value = false
  }
}

const resetSearch = () => {
  searchForm.title = ''
  searchForm.status = null
  loadArticles()
}

const handleSelectionChange = (selection) => {
  selectedArticles.value = selection.map(item => item.id)
}

const handleDelete = async (id) => {
  try {
    await ElMessageBox.confirm('确定要删除这篇文章吗？', '提示', {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning'
    })
    await adminDeleteArticle(id)
    ElMessage.success('删除成功')
    loadArticles()
  } catch (error) {
    if (error !== 'cancel') {
      ElMessage.error('删除失败')
    }
  }
}

const handleBatchDelete = async () => {
  try {
    await ElMessageBox.confirm(`确定要删除选中的 ${selectedArticles.value.length} 篇文章吗？`, '提示', {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning'
    })
    await adminBatchDeleteArticles(selectedArticles.value)
    ElMessage.success('批量删除成功')
    selectedArticles.value = []
    loadArticles()
  } catch (error) {
    if (error !== 'cancel') {
      ElMessage.error('批量删除失败')
    }
  }
}

onMounted(() => {
  loadArticles()
})
</script>

<style scoped lang="scss">
.article-page {
  .card-header {
    display: flex;
    justify-content: space-between;
    align-items: center;
  }

  .search-form {
    margin-bottom: 20px;
  }

  .batch-actions {
    margin-top: 20px;
  }
}
</style>

