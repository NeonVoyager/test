import request from './index'

// 获取文章评论
export function getCommentsByArticleId(articleId) {
  return request({
    url: `/comments/article/${articleId}`,
    method: 'get'
  })
}

// 添加评论
export function addComment(data) {
  return request({
    url: '/comments',
    method: 'post',
    data
  })
}

// 回复评论
export function replyComment(parentId, data) {
  return request({
    url: `/comments/reply/${parentId}`,
    method: 'post',
    data
  })
}

// 后台：获取评论列表
export function adminGetCommentList(params) {
  return request({
    url: '/admin/comment/list',
    method: 'get',
    params
  })
}

// 后台：审核评论
export function adminAuditComment(id, status) {
  return request({
    url: `/admin/comment/audit/${id}`,
    method: 'post',
    params: { status }
  })
}

// 后台：删除评论
export function adminDeleteComment(id) {
  return request({
    url: `/admin/comment/${id}`,
    method: 'delete'
  })
}

// 后台：回复评论
export function adminReplyComment(id, data) {
  return request({
    url: `/admin/comment/reply/${id}`,
    method: 'post',
    data
  })
}

