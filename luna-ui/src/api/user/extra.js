import request from '@/utils/request'

// 查询会员信息列表
export function listExtra (query) {
  return request ({
    url: '/user/extra/list',
    method: 'get',
    params: query
  })
}

// 查询会员信息详细
export function getExtra (userId) {
  return request ({
    url: '/user/extra/' + userId,
    method: 'get'
  })
}

// 新增会员信息
export function addExtra (data) {
  return request ({
    url: '/user/extra',
    method: 'post',
    data: data
  })
}

// 修改会员信息
export function updateExtra (data) {
  return request ({
    url: '/user/extra',
    method: 'put',
    data: data
  })
}

// 删除会员信息
export function delExtra (userId) {
  return request ({
    url: '/user/extra/' + userId,
    method: 'delete'
  })
}

// 会员信息状态修改
export function changeUserFlag (userId, delFlag) {
  const data = {
    userId,
    delFlag
  }
  return request ({
    url: '/user/extra/' + userId,
    method: 'put',
    data: data
  })
}

