import Cookies from 'js-cookie'
import request from "@/utils/request";

const TokenKey = 'Admin-Token'

export function getToken() {
  return Cookies.get(TokenKey)
}

export function setToken(token) {
  return Cookies.set(TokenKey, token)
}

export function removeToken() {
  return Cookies.remove(TokenKey)
}

// 获取oss签名
export function getOssPolicy(data) {
  return request({
    url: '/common/ossPolicy',
    method: 'post',
    data: data
  })
}
