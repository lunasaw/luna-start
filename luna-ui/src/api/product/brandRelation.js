import request from '@/utils/request'

// 查询品牌分类关联列表
export function listBrandRelation(query) {
  return request({
    url: '/product/brandRelation/list',
    method: 'get',
    params: query
  })
}

// 查询品牌分类关联列表
export function listPageBrandRelation(query) {
  return request({
    url: '/product/brandRelation/listPage',
    method: 'get',
    params: query
  })
}


// 查询全部品牌分类关联列表
export function brandRelationListAll(query) {
  return request({
    url: '/product/brandRelation/listAll',
    method: 'get',
    params: query
  })
}

// 查询全部品牌分类关联列表
export function brandRelationListByIds(ids) {
  let query = {
    ids: ids
  }
  return request({
    url: '/product/brandRelation/listByIds',
    method: 'get',
    params: query
  })
}

// 查询品牌分类关联详细
export function getBrandRelation(id) {
  return request({
    url: '/product/brandRelation/' + id,
    method: 'get'
  })
}

// 新增品牌分类关联
export function addBrandRelation(data) {
  return request({
    url: '/product/brandRelation',
    method: 'post',
    data: data
  })
}

// 批量新增品牌分类关联
export function addListBrandRelation(data) {
  return request({
    url: '/product/brandRelation/addList',
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
  return updateBrandRelation(data);
}

// 修改品牌分类关联
export function updateBrandRelation(data) {
  return request({
    url: '/product/brandRelation',
    method: 'put',
    data: data
  })
}

// 批量修改品牌分类关联
export function updateListBrandRelation(data) {
  return request({
    url: '/product/brandRelation/editList',
    method: 'put',
    data: data
  })
}


// 删除品牌分类关联
export function delBrandRelation(id) {
  return request({
    url: '/product/brandRelation/' + id,
    method: 'delete'
  })
}

// 逻辑删除品牌分类关联
export function deleteBrandRelation(data) {
  return request({
    url: '/product/brandRelation/delete',
    method: 'delete',
    data: data
  })
}

// 逻辑批量删除品牌分类关联
export function deleteBatchBrandRelation(data) {
  return request({
    url: '/product/brandRelation/deleteBatch',
    method: 'delete',
    data: data
  })
}

