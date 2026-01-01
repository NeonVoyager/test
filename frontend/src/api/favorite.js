import request from './index'

// 收藏文章
export function addFavorite(articleId, userId) {
  return request({
    url: `/favorites/${articleId}`,
    method: 'post',
    params: { userId }
  })
}

// 取消收藏
export function removeFavorite(articleId, userId) {
  return request({
    url: `/favorites/${articleId}`,
    method: 'delete',
    params: { userId }
  })
}

// 检查是否已收藏
export function isFavorited(userId, articleId) {
  return request({
    url: '/favorites/check',
    method: 'get',
    params: { userId, articleId }
  })
}

// 获取用户收藏列表
export function getUserFavorites(userId) {
  return request({
    url: `/favorites/user/${userId}`,
    method: 'get'
  })
}

