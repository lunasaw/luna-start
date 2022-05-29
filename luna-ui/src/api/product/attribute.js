import request from '@/utils/request'

// 查询商品属性参数列表
export function listAttribute(query) {
  return request({
    url: '/product/attribute/list',
    method: 'get',
    params: query
  })
}

// 批量设置属性分组关联
export function fixCategory(query) {
  return request({
    url: '/product/attribute/fixCategory',
    method: 'put',
    data: query
  })
}

export function listPageFilterAttribute(query) {
  return request({
    url: '/product/attribute/listPageFilter',
    method: 'get',
    params: query
  })
}

// 查询商品属性参数列表
export function listPageAttribute(query) {
  return request({
    url: '/product/attribute/listPage',
    method: 'get',
    params: query
  })
}


// 查询全部商品属性参数列表
export function attributeListAll(query) {
  return request({
    url: '/product/attribute/listAll',
    method: 'get',
    params: query
  })
}

// 查询全部商品属性参数列表
export function attributeListByIds(ids) {
  let query = {
    ids: ids
  }
  return request({
    url: '/product/attribute/listByIds',
    method: 'get',
    params: query
  })
}

// 查询商品属性参数详细
export function getAttribute(id) {
  return request({
    url: '/product/attribute/' + id,
    method: 'get'
  })
}

// 新增商品属性参数
export function addAttribute(data) {
  return request({
    url: '/product/attribute',
    method: 'post',
    data: data
  })
}

// 批量新增商品属性参数
export function addListAttribute(data) {
  return request({
    url: '/product/attribute/addList',
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
  return updateAttribute(data);
}

// 修改商品属性参数
export function updateAttribute(data) {
  return request({
    url: '/product/attribute',
    method: 'put',
    data: data
  })
}

// 批量修改商品属性参数
export function updateListAttribute(data) {
  return request({
    url: '/product/attribute/editList',
    method: 'put',
    data: data
  })
}


// 删除商品属性参数
export function delAttribute(id) {
  return request({
    url: '/product/attribute/' + id,
    method: 'delete'
  })
}

// 逻辑删除商品属性参数
export function deleteAttribute(data) {
  return request({
    url: '/product/attribute/delete',
    method: 'delete',
    data: data
  })
}

