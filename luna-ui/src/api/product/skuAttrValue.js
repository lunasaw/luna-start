import request from '@/utils/request'

// 查询sku销售属性&值列表
export function listSkuAttrValue(query) {
  return request({
    url: '/product/skuAttrValue/list',
    method: 'get',
    params: query
  })
}

// 查询sku销售属性&值列表
export function listPageSkuAttrValue(query) {
  return request({
    url: '/product/skuAttrValue/listPage',
    method: 'get',
    params: query
  })
}


// 查询全部sku销售属性&值列表
export function skuAttrValueListAll(query) {
  return request({
    url: '/product/skuAttrValue/listAll',
    method: 'get',
    params: query
  })
}

// 查询全部sku销售属性&值列表
export function skuAttrValueListByIds(ids) {
  let query = {
    ids: ids
  }
  return request({
    url: '/product/skuAttrValue/listByIds',
    method: 'get',
    params: query
  })
}

// 查询sku销售属性&值详细
export function getSkuAttrValue(id) {
  return request({
    url: '/product/skuAttrValue/' + id,
    method: 'get'
  })
}

// 新增sku销售属性&值
export function addSkuAttrValue(data) {
  return request({
    url: '/product/skuAttrValue',
    method: 'post',
    data: data
  })
}

// 批量新增sku销售属性&值
export function addListSkuAttrValue(data) {
  return request({
    url: '/product/skuAttrValue/addList',
    method: 'post',
    data: data
  })
}


// 修改sku销售属性&值
export function updateSkuAttrValue(data) {
  return request({
    url: '/product/skuAttrValue',
    method: 'put',
    data: data
  })
}

// 批量修改sku销售属性&值
export function updateListSkuAttrValue(data) {
  return request({
    url: '/product/skuAttrValue/editList',
    method: 'put',
    data: data
  })
}


// 删除sku销售属性&值
export function delSkuAttrValue(id) {
  return request({
    url: '/product/skuAttrValue/' + id,
    method: 'delete'
  })
}

// 逻辑删除sku销售属性&值
export function deleteSkuAttrValue(data) {
  return request({
    url: '/product/skuAttrValue/delete',
    method: 'delete',
    data: data
  })
}

// 逻辑批量删除sku销售属性&值
export function deleteBatchSkuAttrValue(data) {
  return request({
    url: '/product/skuAttrValue/deleteBatch',
    method: 'delete',
    data: data
  })
}

