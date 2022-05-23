import request from '@/utils/request'

// 获取服务信息
export function getServer() {
  return request({
    url: '/monitor/server',
    method: 'get'
  })
}

// 获取oss签名
export function getOssPolicy(data) {
  return request({
    url: '/common/ossPolicy',
    method: 'post',
    data: data
  })
}
