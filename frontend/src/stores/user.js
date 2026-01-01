import { defineStore } from 'pinia'
import { login, logout, getCurrentUser } from '@/api/user'

export const useUserStore = defineStore('user', {
  state: () => ({
    user: null,
    token: localStorage.getItem('token') || ''
  }),

  getters: {
    isLoggedIn: (state) => !!state.token,
    username: (state) => state.user?.username || '',
    role: (state) => state.user?.role || ''
  },

  actions: {
    async login(username, password) {
      try {
        const res = await login(username, password)
        this.token = res.data.token
        this.user = res.data.user
        localStorage.setItem('token', this.token)
        return Promise.resolve(res)
      } catch (error) {
        return Promise.reject(error)
      }
    },

    async logout() {
      try {
        await logout()
        this.token = ''
        this.user = null
        localStorage.removeItem('token')
      } catch (error) {
        console.error('登出失败:', error)
      }
    },

    async getCurrentUser() {
      try {
        const res = await getCurrentUser()
        this.user = res.data
        return Promise.resolve(res)
      } catch (error) {
        this.token = ''
        this.user = null
        localStorage.removeItem('token')
        return Promise.reject(error)
      }
    }
  }
})

