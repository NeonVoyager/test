import request from './index'

// 获取统计数据
export function getStatistics() {
  return request({
    url: '/admin/dashboard/statistics',
    method: 'get'
  })
}

// 获取系统信息
export function getSystemInfo() {
  return request({
    url: '/admin/monitor/system',
    method: 'get'
  })
}

// 获取数据源信息
export function getDatasourceInfo() {
  return request({
    url: '/admin/monitor/datasource',
    method: 'get'
  })
}

