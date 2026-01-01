import request from './index'

// 举报评论
export function reportComment(commentId, userId, reason) {
  return request({
    url: `/report/comment/${commentId}`,
    method: 'post',
    params: { userId, reason }
  })
}

// 后台：获取举报列表
export function adminGetReportList(params) {
  return request({
    url: '/admin/report/list',
    method: 'get',
    params
  })
}

// 后台：处理举报
export function adminHandleReport(id, status, handleResult) {
  return request({
    url: `/admin/report/handle/${id}`,
    method: 'post',
    params: { status, handleResult }
  })
}

