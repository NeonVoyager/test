import request from './index'

// 获取标签列表
export function getTagList() {
  return request({
    url: '/tags',
    method: 'get'
  })
}

// 后台：获取标签列表
export function adminGetTagList() {
  return request({
    url: '/admin/tag/list',
    method: 'get'
  })
}

// 后台：保存标签
export function adminSaveTag(data) {
  return request({
    url: '/admin/tag',
    method: 'post',
    data
  })
}

// 后台：删除标签
export function adminDeleteTag(id) {
  return request({
    url: `/admin/tag/${id}`,
    method: 'delete'
  })
}

