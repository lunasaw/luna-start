import request from '@/utils/request'

// 查询SKU图片列表
export function listSkuImages(query) {
  return request({
    url: '/product/skuImages/list',
    method: 'get',
    params: query
  })
}

// 查询SKU图片列表
export function listPageSkuImages(query) {
  return request({
    url: '/product/skuImages/listPage',
    method: 'get',
    params: query
  })
}


// 查询全部SKU图片列表
export function skuImagesListAll(query) {
  return request({
    url: '/product/skuImages/listAll',
    method: 'get',
    params: query
  })
}

// 查询全部SKU图片列表
export function skuImagesListByIds(ids) {
  let query = {
    ids: ids
  }
  return request({
    url: '/product/skuImages/listByIds',
    method: 'get',
    params: query
  })
}

// 查询SKU图片详细
export function getSkuImages(id) {
  return request({
    url: '/product/skuImages/' + id,
    method: 'get'
  })
}

// 新增SKU图片
export function addSkuImages(data) {
  return request({
    url: '/product/skuImages',
    method: 'post',
    data: data
  })
}

// 批量新增SKU图片
export function addListSkuImages(data) {
  return request({
    url: '/product/skuImages/addList',
    method: 'post',
    data: data
  })
}

// 修改 是否默认图 状态
export function defaultImgSwitchChange(id, defaultImg) {
  const data = {
    id,
    defaultImg
  }
  return updateSkuImages(data);
}

// 修改 是否删除 状态
export function deletedSwitchChange(id, deleted) {
  const data = {
    id,
    deleted
  }
  return updateSkuImages(data);
}

// 修改SKU图片
export function updateSkuImages(data) {
  return request({
    url: '/product/skuImages',
    method: 'put',
    data: data
  })
}

// 批量修改SKU图片
export function updateListSkuImages(data) {
  return request({
    url: '/product/skuImages/editList',
    method: 'put',
    data: data
  })
}


// 删除SKU图片
export function delSkuImages(id) {
  return request({
    url: '/product/skuImages/' + id,
    method: 'delete'
  })
}

// 逻辑删除SKU图片
export function deleteSkuImages(data) {
  return request({
    url: '/product/skuImages/delete',
    method: 'delete',
    data: data
  })
}

// 逻辑批量删除SKU图片
export function deleteBatchSkuImages(data) {
  return request({
    url: '/product/skuImages/deleteBatch',
    method: 'delete',
    data: data
  })
}

