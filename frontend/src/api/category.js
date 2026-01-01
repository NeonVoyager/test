import request from './index'

// 获取分类列表
export function getCategoryList() {
  return request({
    url: '/categories',
    method: 'get'
  })
}

// 后台：获取分类列表
export function adminGetCategoryList() {
  return request({
    url: '/admin/category/list',
    method: 'get'
  })
}

// 后台：保存分类
export function adminSaveCategory(data) {
  return request({
    url: '/admin/category',
    method: 'post',
    data
  })
}

// 后台：删除分类
export function adminDeleteCategory(id) {
  return request({
    url: `/admin/category/${id}`,
    method: 'delete'
  })
}

