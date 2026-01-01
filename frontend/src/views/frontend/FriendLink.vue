<template>
  <FrontendLayout>
    <div class="friend-link-page">
      <div class="container">
        <el-row :gutter="20">
          <el-col :xs="24" :sm="24" :md="18" :lg="18">
            <el-card>
              <h2>友情链接</h2>
              <div v-loading="loading" class="friend-link-list">
                <div
                  v-for="link in friendLinks"
                  :key="link.id"
                  class="friend-link-item"
                >
                  <img v-if="link.logo" :src="link.logo" :alt="link.name" class="link-logo" />
                  <div class="link-info">
                    <a :href="link.url" target="_blank" class="link-name">{{ link.name }}</a>
                    <p class="link-desc">{{ link.description }}</p>
                  </div>
                </div>
                <el-empty v-if="!loading && friendLinks.length === 0" description="暂无友情链接" />
              </div>

              <!-- 申请友链 -->
              <el-card class="apply-card" shadow="never">
                <h3>申请友情链接</h3>
                <el-form :model="applyForm" label-width="100px">
                  <el-form-item label="网站名称" required>
                    <el-input v-model="applyForm.name" placeholder="请输入网站名称" />
                  </el-form-item>
                  <el-form-item label="网站地址" required>
                    <el-input v-model="applyForm.url" placeholder="https://" />
                  </el-form-item>
                  <el-form-item label="Logo地址">
                    <el-input v-model="applyForm.logo" placeholder="https://" />
                  </el-form-item>
                  <el-form-item label="网站描述">
                    <el-input
                      v-model="applyForm.description"
                      type="textarea"
                      :rows="3"
                      placeholder="请输入网站描述"
                    />
                  </el-form-item>
                  <el-form-item>
                    <el-button type="primary" @click="submitApply" :loading="applyLoading">
                      提交申请
                    </el-button>
                  </el-form-item>
                </el-form>
              </el-card>
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
import { getFriendLinkList, applyFriendLink } from '@/api/friendLink'
import { ElMessage } from 'element-plus'

const friendLinks = ref([])
const loading = ref(false)

const applyForm = ref({
  name: '',
  url: '',
  logo: '',
  description: ''
})
const applyLoading = ref(false)

const loadFriendLinks = async () => {
  loading.value = true
  try {
    const res = await getFriendLinkList()
    friendLinks.value = res.data
  } catch (error) {
    console.error('加载友情链接失败:', error)
  } finally {
    loading.value = false
  }
}

const submitApply = async () => {
  if (!applyForm.value.name || !applyForm.value.url) {
    ElMessage.warning('请填写网站名称和地址')
    return
  }
  
  applyLoading.value = true
  try {
    await applyFriendLink(applyForm.value)
    ElMessage.success('申请提交成功，等待审核')
    applyForm.value = {
      name: '',
      url: '',
      logo: '',
      description: ''
    }
  } catch (error) {
    ElMessage.error('提交失败')
  } finally {
    applyLoading.value = false
  }
}

onMounted(() => {
  loadFriendLinks()
})
</script>

<style scoped lang="scss">
.friend-link-page {
  .container {
    max-width: 1200px;
    margin: 0 auto;
    padding: 0 20px;
  }

  .friend-link-list {
    margin-top: 20px;

    .friend-link-item {
      display: flex;
      align-items: center;
      padding: 20px;
      border-bottom: 1px solid #f0f0f0;

      &:last-child {
        border-bottom: none;
      }

      .link-logo {
        width: 80px;
        height: 80px;
        object-fit: cover;
        border-radius: 8px;
        margin-right: 20px;
      }

      .link-info {
        flex: 1;

        .link-name {
          font-size: 18px;
          font-weight: bold;
          color: #409eff;
          display: block;
          margin-bottom: 10px;
        }

        .link-desc {
          color: #666;
          margin: 0;
        }
      }
    }
  }

  .apply-card {
    margin-top: 40px;
    padding-top: 20px;
    border-top: 1px solid #f0f0f0;

    h3 {
      margin-bottom: 20px;
    }
  }
}
</style>

