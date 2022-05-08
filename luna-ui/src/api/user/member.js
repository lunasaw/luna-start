import request from '@/utils/request'

// 查询会员管理列表
export function listMember(query) {
  return request({
    url: '/user/member/list',
    method: 'get',
    params: query
  })
}

// 查询会员管理详细
export function getMember(userId) {
  return request({
    url: '/user/member/' + userId,
    method: 'get'
  })
}

// 新增会员管理
export function addMember(data) {
  return request({
    url: '/user/member',
    method: 'post',
    data: data
  })
}

// 修改会员管理
export function updateMember(data) {
  return request({
    url: '/user/member',
    method: 'put',
    data: data
  })
}

// 删除会员管理
export function delMember(userId) {
  return request({
    url: '/user/member/' + userId,
    method: 'delete'
  })
}

// 会员状态修改
export function changeUserFlag (userId, delFlag) {
  const data = {
    userId,
    delFlag
  }
  return request ({
    url: '/user/member/' + userId,
    method: 'put',
    data: data
  })
}
