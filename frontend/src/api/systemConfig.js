import request from './index'

// 后台：获取所有配置
export function getAllConfigs() {
  return request({
    url: '/admin/system-config/all',
    method: 'get'
  })
}

// 后台：保存配置
export function saveConfig(data) {
  return request({
    url: '/admin/system-config',
    method: 'post',
    data
  })
}

