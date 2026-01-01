import request from './index'

// 添加浏览历史
export function addHistory(articleId, userId) {
  return request({
    url: `/history/${articleId}`,
    method: 'post',
    params: { userId }
  })
}

// 获取用户浏览历史
export function getUserHistory(userId, limit = 20) {
  return request({
    url: `/history/user/${userId}`,
    method: 'get',
    params: { limit }
  })
}

// 清空浏览历史
export function clearHistory(userId) {
  return request({
    url: `/history/user/${userId}`,
    method: 'delete'
  })
}

