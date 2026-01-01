import request from './index'

// 获取友情链接列表
export function getFriendLinkList() {
  return request({
    url: '/friend-link/list',
    method: 'get'
  })
}

// 申请友情链接
export function applyFriendLink(data) {
  return request({
    url: '/friend-link/apply',
    method: 'post',
    data
  })
}

// 后台：获取友情链接列表
export function adminGetFriendLinkList(params) {
  return request({
    url: '/admin/friend-link/list',
    method: 'get',
    params
  })
}

// 后台：审核友情链接
export function adminAuditFriendLink(id, status) {
  return request({
    url: `/admin/friend-link/audit/${id}`,
    method: 'post',
    params: { status }
  })
}

// 后台：删除友情链接
export function adminDeleteFriendLink(id) {
  return request({
    url: `/admin/friend-link/${id}`,
    method: 'delete'
  })
}

