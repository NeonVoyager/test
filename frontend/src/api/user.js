import request from './index'

// 登录
export function login(username, password) {
  return request({
    url: '/admin/login',
    method: 'post',
    params: { username, password }
  })
}

// 登出
export function logout() {
  return request({
    url: '/admin/logout',
    method: 'post'
  })
}

// 获取当前用户
export function getCurrentUser() {
  return request({
    url: '/admin/current-user',
    method: 'get'
  })
}

// 后台：获取用户列表
export function adminGetUserList(params) {
  return request({
    url: '/admin/user/list',
    method: 'get',
    params
  })
}

// 后台：保存用户
export function adminSaveUser(data) {
  return request({
    url: '/admin/user',
    method: 'post',
    data
  })
}

// 后台：重置密码
export function adminResetPassword(id, newPassword) {
  return request({
    url: `/admin/user/reset-password/${id}`,
    method: 'post',
    params: { newPassword }
  })
}

// 后台：删除用户
export function adminDeleteUser(id) {
  return request({
    url: `/admin/user/${id}`,
    method: 'delete'
  })
}

