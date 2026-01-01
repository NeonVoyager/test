import request from './index'

// 订阅邮箱提醒
export function subscribe(email) {
  return request({
    url: '/subscribe',
    method: 'post',
    params: { email }
  })
}

// 验证订阅
export function verifySubscribe(email, verifyCode) {
  return request({
    url: '/subscribe/verify',
    method: 'post',
    params: { email, verifyCode }
  })
}

