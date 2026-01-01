import request from './index'

// 获取文章列表
export function getArticleList(params) {
  return request({
    url: '/articles',
    method: 'get',
    params
  })
}

// 获取文章详情
export function getArticleDetail(id) {
  return request({
    url: `/article/${id}`,
    method: 'get'
  })
}

// 点赞文章
export function likeArticle(id) {
  return request({
    url: `/article/${id}/like`,
    method: 'post'
  })
}

// 获取热门文章
export function getHotArticles(limit = 10) {
  return request({
    url: '/articles/hot',
    method: 'get',
    params: { limit }
  })
}

// 获取置顶文章
export function getTopArticles(limit = 5) {
  return request({
    url: '/articles/top',
    method: 'get',
    params: { limit }
  })
}

// 搜索文章
export function searchArticles(keyword, params) {
  return request({
    url: '/articles/search',
    method: 'get',
    params: { keyword, ...params }
  })
}

// 按分类获取文章
export function getArticlesByCategory(categoryId, params) {
  return request({
    url: `/articles/category/${categoryId}`,
    method: 'get',
    params
  })
}

// 按标签获取文章
export function getArticlesByTag(tagName, params) {
  return request({
    url: `/articles/tag/${tagName}`,
    method: 'get',
    params
  })
}

// 获取归档文章
export function getArchiveArticles(year, month) {
  return request({
    url: '/articles/archive',
    method: 'get',
    params: { year, month }
  })
}

// 获取推荐文章
export function getRecommendArticles(articleId, limit = 5) {
  return request({
    url: `/articles/recommend/${articleId}`,
    method: 'get',
    params: { limit }
  })
}

// 后台：获取文章列表
export function adminGetArticleList(params) {
  return request({
    url: '/admin/article/list',
    method: 'get',
    params
  })
}

// 后台：获取文章详情
export function adminGetArticle(id) {
  return request({
    url: `/admin/article/${id}`,
    method: 'get'
  })
}

// 后台：保存文章
export function adminSaveArticle(data) {
  return request({
    url: '/admin/article',
    method: 'post',
    data
  })
}

// 后台：删除文章
export function adminDeleteArticle(id) {
  return request({
    url: `/admin/article/${id}`,
    method: 'delete'
  })
}

// 后台：批量删除文章
export function adminBatchDeleteArticles(ids) {
  return request({
    url: '/admin/article/batch',
    method: 'delete',
    data: ids
  })
}

