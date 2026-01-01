<template>
  <router-view />
</template>

<script setup>
import { onMounted } from 'vue'
import { useUserStore } from './stores/user'

const userStore = useUserStore()

onMounted(() => {
  // 检查登录状态
  const token = localStorage.getItem('token')
  if (token) {
    userStore.getCurrentUser()
  }
})
</script>

<style>
#app {
  min-height: 100vh;
}
</style>

