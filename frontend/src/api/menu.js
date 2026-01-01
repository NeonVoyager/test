import request from './index'

// 后台：获取菜单列表
export function adminGetMenuList() {
  return request({
    url: '/admin/menu/list',
    method: 'get'
  })
}

// 后台：保存菜单
export function adminSaveMenu(data) {
  return request({
    url: '/admin/menu',
    method: 'post',
    data
  })
}

// 后台：删除菜单
export function adminDeleteMenu(id) {
  return request({
    url: `/admin/menu/${id}`,
    method: 'delete'
  })
}

