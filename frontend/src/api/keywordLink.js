import request from './index'

// 后台：获取关键字链接列表
export function adminGetKeywordLinkList(params) {
  return request({
    url: '/admin/keyword-link/list',
    method: 'get',
    params
  })
}

// 后台：保存关键字链接
export function adminSaveKeywordLink(data) {
  return request({
    url: '/admin/keyword-link',
    method: 'post',
    data
  })
}

// 后台：删除关键字链接
export function adminDeleteKeywordLink(id) {
  return request({
    url: `/admin/keyword-link/${id}`,
    method: 'delete'
  })
}

