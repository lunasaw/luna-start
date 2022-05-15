import request from '@/utils/request'

// 查询产品属性分类列表
export function listAttributeCategory(query) {
  return request({
    url: '/product/attributeCategory/list',
    method: 'get',
    params: query
  })
}

// 查询产品属性分类列表
export function listPageAttributeCategory(query) {
  return request({
    url: '/product/attributeCategory/listPage',
    method: 'get',
    params: query
  })
}


// 查询全部产品属性分类列表
export function attributeCategoryListAll(query) {
  return request({
    url: '/product/attributeCategory/listAll',
    method: 'get',
    params: query
  })
}

// 查询全部产品属性分类列表
export function attributeCategoryListByIds(ids) {
  let query = {
    ids: ids
  }
  return request({
    url: '/product/attributeCategory/listByIds',
    method: 'get',
    params: query
  })
}

// 查询产品属性分类详细
export function getAttributeCategory(id) {
  return request({
    url: '/product/attributeCategory/' + id,
    method: 'get'
  })
}

// 新增产品属性分类
export function addAttributeCategory(data) {
  return request({
    url: '/product/attributeCategory',
    method: 'post',
    data: data
  })
}

// 批量新增产品属性分类
export function addListAttributeCategory(data) {
  return request({
    url: '/product/attributeCategory/addList',
    method: 'post',
    data: data
  })
}

// 修改 是否删除 状态
export function deletedSwitchChange(id, deleted) {
  const data = {
    id,
    deleted
  }
  return updateAttributeCategory(data);
}

// 修改产品属性分类
export function updateAttributeCategory(data) {
  return request({
    url: '/product/attributeCategory',
    method: 'put',
    data: data
  })
}

// 批量修改产品属性分类
export function updateListAttributeCategory(data) {
  return request({
    url: '/product/attributeCategory/editList',
    method: 'put',
    data: data
  })
}


// 删除产品属性分类
export function delAttributeCategory(id) {
  return request({
    url: '/product/attributeCategory/' + id,
    method: 'delete'
  })
}

// 逻辑删除产品属性分类
export function deleteAttributeCategory(data) {
  return request({
    url: '/product/attributeCategory/delete',
    method: 'delete',
    data: data
  })
}

