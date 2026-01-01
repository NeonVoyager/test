<template>
  <FrontendLayout>
    <div class="about-page">
      <div class="container">
        <el-row :gutter="20">
          <el-col :xs="24" :sm="24" :md="18" :lg="18">
            <el-card>
              <h1>关于我</h1>
              <div class="about-content" v-html="aboutContent"></div>
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
import request from '@/api/index'

const aboutContent = ref('')

const loadAbout = async () => {
  try {
    const res = await request({
      url: '/admin/system-config/all',
      method: 'get'
    })
    aboutContent.value = res.data.about_content || '暂无内容'
  } catch (error) {
    console.error('加载关于内容失败:', error)
    aboutContent.value = '暂无内容'
  }
}

onMounted(() => {
  loadAbout()
})
</script>

<style scoped lang="scss">
.about-page {
  .container {
    max-width: 1200px;
    margin: 0 auto;
    padding: 0 20px;
  }

  .about-content {
    line-height: 1.8;
    font-size: 16px;
    margin-top: 20px;
  }
}
</style>

