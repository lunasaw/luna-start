import request from '@/utils/request'

// 查询sku信息列表
export function listSkuInfo(query) {
  return request({
    url: '/product/skuInfo/list',
    method: 'get',
    params: query
  })
}

// 查询sku信息列表
export function listPageSkuInfo(query) {
  return request({
    url: '/product/skuInfo/listPage',
    method: 'get',
    params: query
  })
}


// 查询全部sku信息列表
export function skuInfoListAll(query) {
  return request({
    url: '/product/skuInfo/listAll',
    method: 'get',
    params: query
  })
}

// 查询全部sku信息列表
export function skuInfoListByIds(ids) {
  let query = {
    ids: ids
  }
  return request({
    url: '/product/skuInfo/listByIds',
    method: 'get',
    params: query
  })
}

// 查询sku信息详细
export function getSkuInfo(skuId) {
  return request({
    url: '/product/skuInfo/' + skuId,
    method: 'get'
  })
}

// 新增sku信息
export function addSkuInfo(data) {
  return request({
    url: '/product/skuInfo',
    method: 'post',
    data: data
  })
}

// 批量新增sku信息
export function addListSkuInfo(data) {
  return request({
    url: '/product/skuInfo/addList',
    method: 'post',
    data: data
  })
}


// 修改sku信息
export function updateSkuInfo(data) {
  return request({
    url: '/product/skuInfo',
    method: 'put',
    data: data
  })
}

// 批量修改sku信息
export function updateListSkuInfo(data) {
  return request({
    url: '/product/skuInfo/editList',
    method: 'put',
    data: data
  })
}


// 删除sku信息
export function delSkuInfo(skuId) {
  return request({
    url: '/product/skuInfo/' + skuId,
    method: 'delete'
  })
}

// 逻辑删除sku信息
export function deleteSkuInfo(data) {
  return request({
    url: '/product/skuInfo/delete',
    method: 'delete',
    data: data
  })
}

// 逻辑批量删除sku信息
export function deleteBatchSkuInfo(data) {
  return request({
    url: '/product/skuInfo/deleteBatch',
    method: 'delete',
    data: data
  })
}

