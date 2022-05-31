import request from '@/utils/request'

// 查询sku信息列表
export function listSkuInfo(query) {
    return request({
        url: '/skuInfo/skuInfo/list',
        method: 'get',
        params: query
    })
}

// 查询sku信息列表
export function listPageSkuInfo(query) {
    return request({
        url: '/skuInfo/skuInfo/listPage',
        method: 'get',
        params: query
    })
}


// 查询全部sku信息列表
export function skuInfoListAll(query) {
    return request({
        url: '/skuInfo/skuInfo/listAll',
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
        url: '/skuInfo/skuInfo/listByIds',
        method: 'get',
        params: query
    })
}

// 查询sku信息详细
export function getSkuInfo(skuId) {
    return request({
        url: '/skuInfo/skuInfo/' + skuId,
        method: 'get'
    })
}

// 新增sku信息
export function addSkuInfo(data) {
    return request({
        url: '/skuInfo/skuInfo',
        method: 'post',
        data: data
    })
}

// 批量新增sku信息
export function addListSkuInfo(data) {
    return request({
        url: '/skuInfo/skuInfo/addList',
        method: 'post',
        data: data
    })
}


// 修改sku信息
export function updateSkuInfo(data) {
    return request({
        url: '/skuInfo/skuInfo',
        method: 'put',
        data: data
    })
}

// 批量修改sku信息
export function updateListSkuInfo(data) {
    return request({
        url: '/skuInfo/skuInfo/editList',
        method: 'put',
        data: data
    })
}


// 删除sku信息
export function delSkuInfo(skuId) {
    return request({
        url: '/skuInfo/skuInfo/' + skuId,
        method: 'delete'
    })
}

// 逻辑删除sku信息
export function deleteSkuInfo(data) {
    return request({
        url: '/skuInfo/skuInfo/delete',
        method: 'delete',
        data: data
    })
}

// 逻辑批量删除sku信息
export function deleteBatchSkuInfo(data) {
    return request({
        url: '/skuInfo/skuInfo/deleteBatch',
        method: 'delete',
        data: data
    })
}

