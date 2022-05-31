import request from '@/utils/request'

// 查询sku图片列表
export function listSkuImages(query) {
    return request({
        url: '/skuImages/skuImages/list',
        method: 'get',
        params: query
    })
}

// 查询sku图片列表
export function listPageSkuImages(query) {
    return request({
        url: '/skuImages/skuImages/listPage',
        method: 'get',
        params: query
    })
}


// 查询全部sku图片列表
export function skuImagesListAll(query) {
    return request({
        url: '/skuImages/skuImages/listAll',
        method: 'get',
        params: query
    })
}

// 查询全部sku图片列表
export function skuImagesListByIds(ids) {
    let query = {
        ids: ids
    }
    return request({
        url: '/skuImages/skuImages/listByIds',
        method: 'get',
        params: query
    })
}

// 查询sku图片详细
export function getSkuImages(id) {
    return request({
        url: '/skuImages/skuImages/' + id,
        method: 'get'
    })
}

// 新增sku图片
export function addSkuImages(data) {
    return request({
        url: '/skuImages/skuImages',
        method: 'post',
        data: data
    })
}

// 批量新增sku图片
export function addListSkuImages(data) {
    return request({
        url: '/skuImages/skuImages/addList',
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

// 修改sku图片
export function updateSkuImages(data) {
    return request({
        url: '/skuImages/skuImages',
        method: 'put',
        data: data
    })
}

// 批量修改sku图片
export function updateListSkuImages(data) {
    return request({
        url: '/skuImages/skuImages/editList',
        method: 'put',
        data: data
    })
}


// 删除sku图片
export function delSkuImages(id) {
    return request({
        url: '/skuImages/skuImages/' + id,
        method: 'delete'
    })
}

// 逻辑删除sku图片
export function deleteSkuImages(data) {
    return request({
        url: '/skuImages/skuImages/delete',
        method: 'delete',
        data: data
    })
}

// 逻辑批量删除sku图片
export function deleteBatchSkuImages(data) {
    return request({
        url: '/skuImages/skuImages/deleteBatch',
        method: 'delete',
        data: data
    })
}

