import request from '@/utils/request'

// 查询产品属性分类列表
export function listCategoryAttribute(query) {
  return request({
    url: '/product/categoryAttribute/list',
    method: 'get',
    params: query
  })
}

// 查询全部产品属性分类列表
export function categoryAttributeListAll(query) {
  return request({
    url: '/product/categoryAttribute/listAll',
    method: 'get',
    params: query
  })
}

// 查询全部产品属性分类列表
export function categoryAttributeListByIds(ids) {
  let query = {
    ids: ids
  }
  return request({
    url: '/product/categoryAttribute/listByIds',
    method: 'get',
    params: query
  })
}

// 查询产品属性分类详细
export function getCategoryAttribute(id) {
  return request({
    url: '/product/categoryAttribute/' + id,
    method: 'get'
  })
}

// 新增产品属性分类
export function addCategoryAttribute(data) {
  return request({
    url: '/product/categoryAttribute',
    method: 'post',
    data: data
  })
}

// 批量新增产品属性分类
export function addListCategoryAttribute(data) {
  return request({
    url: '/product/categoryAttribute/addList',
    method: 'post',
    data: data
  })
}

// 修改产品属性分类
export function updateCategoryAttribute(data) {
  return request({
    url: '/product/categoryAttribute',
    method: 'put',
    data: data
  })
}

// 批量修改产品属性分类
export function updateListCategoryAttribute(data) {
  return request({
    url: '/product/categoryAttribute/editList',
    method: 'put',
    data: data
  })
}


// 删除产品属性分类
export function delCategoryAttribute(id) {
  return request({
    url: '/product/categoryAttribute/' + id,
    method: 'delete'
  })
}

