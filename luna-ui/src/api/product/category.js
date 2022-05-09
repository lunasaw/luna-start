import request from '@/utils/request'

// 查询产品分类列表
export function listCategory(query) {
  return request({
    url: '/product/category/list',
    method: 'get',
    params: query
  })
}

// 查询全部产品分类列表
export function categoryListAll(query) {
  return request({
    url: '/product/category/listAll',
    method: 'get',
    params: query
  })
}

// 查询全部产品分类列表
export function categoryListByIds(ids) {
  let query = {
    ids: ids
  }
  return request({
    url: '/product/category/listByIds',
    method: 'get',
    params: query
  })
}

// 查询产品分类详细
export function getCategory(id) {
  return request({
    url: '/product/category/' + id,
    method: 'get'
  })
}

// 新增产品分类
export function addCategory(data) {
  return request({
    url: '/product/category',
    method: 'post',
    data: data
  })
}

// 批量新增产品分类
export function addListCategory(data) {
  return request({
    url: '/product/category/addList',
    method: 'post',
    data: data
  })
}

// 修改产品分类
export function updateCategory(data) {
  return request({
    url: '/product/category',
    method: 'put',
    data: data
  })
}

// 批量修改产品分类
export function updateListCategory(data) {
  return request({
    url: '/product/category/editList',
    method: 'put',
    data: data
  })
}


// 删除产品分类
export function delCategory(id) {
  return request({
    url: '/product/category/' + id,
    method: 'delete'
  })
}

