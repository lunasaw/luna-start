import request from '@/utils/request'

// 查询品牌列表
export function listBrand(query) {
  return request({
    url: '/product/brand/list',
    method: 'get',
    params: query
  })
}

// 查询全部品牌列表
export function brandListAll(query) {
  return request({
    url: '/product/brand/listAll',
    method: 'get',
    params: query
  })
}

// 查询全部品牌列表
export function brandListByIds(ids) {
  let query = {
    ids: ids
  }
  return request({
    url: '/product/brand/listByIds',
    method: 'get',
    params: query
  })
}

// 查询品牌详细
export function getBrand(id) {
  return request({
    url: '/product/brand/' + id,
    method: 'get'
  })
}

// 新增品牌
export function addBrand(data) {
  return request({
    url: '/product/brand',
    method: 'post',
    data: data
  })
}

// 批量新增品牌
export function addListBrand(data) {
  return request({
    url: '/product/brand/addList',
    method: 'post',
    data: data
  })
}

// 修改 是否为品牌制造商 状态
export function factoryStatusSwitchChange(id, factoryStatus) {
  const data = {
    id,
    factoryStatus
  }
  return updateBrand(data);
}
// 修改 是否展示 状态
export function showStatusSwitchChange(id, showStatus) {
  const data = {
    id,
    showStatus
  }
  return updateBrand(data);
}

// 修改品牌
export function updateBrand(data) {
  return request({
    url: '/product/brand',
    method: 'put',
    data: data
  })
}

// 批量修改品牌
export function updateListBrand(data) {
  return request({
    url: '/product/brand/editList',
    method: 'put',
    data: data
  })
}


// 删除品牌
export function delBrand(id) {
  return request({
    url: '/product/brand/' + id,
    method: 'delete'
  })
}

