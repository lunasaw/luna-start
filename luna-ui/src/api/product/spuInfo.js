import request from '@/utils/request'

// 查询商品SPU信息列表
export function listSpuInfo(query) {
  return request({
    url: '/product/spuInfo/list',
    method: 'get',
    params: query
  })
}

// 查询商品SPU信息列表
export function listPageSpuInfo(query) {
  return request({
    url: '/product/spuInfo/listPage',
    method: 'get',
    params: query
  })
}


// 查询全部商品SPU信息列表
export function spuInfoListAll(query) {
  return request({
    url: '/product/spuInfo/listAll',
    method: 'get',
    params: query
  })
}

// 查询全部商品SPU信息列表
export function spuInfoListByIds(ids) {
  let query = {
    ids: ids
  }
  return request({
    url: '/product/spuInfo/listByIds',
    method: 'get',
    params: query
  })
}

// 查询商品SPU信息详细
export function getSpuInfo(id) {
  return request({
    url: '/product/spuInfo/' + id,
    method: 'get'
  })
}

// 新增商品SPU信息
export function addSpuInfo(data) {
  return request({
    url: '/product/spuInfo',
    method: 'post',
    data: data
  })
}

// 批量新增商品SPU信息
export function addListSpuInfo(data) {
  return request({
    url: '/product/spuInfo/addList',
    method: 'post',
    data: data
  })
}

// 修改 上架状态 状态
export function publishStatusSwitchChange(id, publishStatus) {
  const data = {
    id,
    publishStatus
  }
  return updateSpuInfo(data);
}

// 修改 是否删除 状态
export function deletedSwitchChange(id, deleted) {
  const data = {
    id,
    deleted
  }
  return updateSpuInfo(data);
}

// 修改商品SPU信息
export function updateSpuInfo(data) {
  return request({
    url: '/product/spuInfo',
    method: 'put',
    data: data
  })
}

// 批量修改商品SPU信息
export function updateListSpuInfo(data) {
  return request({
    url: '/product/spuInfo/editList',
    method: 'put',
    data: data
  })
}


// 删除商品SPU信息
export function delSpuInfo(id) {
  return request({
    url: '/product/spuInfo/' + id,
    method: 'delete'
  })
}

// 逻辑删除商品SPU信息
export function deleteSpuInfo(data) {
  return request({
    url: '/product/spuInfo/delete',
    method: 'delete',
    data: data
  })
}

// 逻辑批量删除商品SPU信息
export function deleteBatchSpuInfo(data) {
  return request({
    url: '/product/spuInfo/deleteBatch',
    method: 'delete',
    data: data
  })
}

