import request from '@/utils/request'

// 查询SPU图片列表
export function listSpuImages(query) {
    return request({
        url: '/product/spuImages/list',
        method: 'get',
        params: query
    })
}

// 查询SPU图片列表
export function listPageSpuImages(query) {
    return request({
        url: '/product/spuImages/listPage',
        method: 'get',
        params: query
    })
}


// 查询全部SPU图片列表
export function spuImagesListAll(query) {
    return request({
        url: '/product/spuImages/listAll',
        method: 'get',
        params: query
    })
}

// 查询全部SPU图片列表
export function spuImagesListByIds(ids) {
    let query = {
        ids: ids
    }
    return request({
        url: '/product/spuImages/listByIds',
        method: 'get',
        params: query
    })
}

// 查询SPU图片详细
export function getSpuImages(id) {
    return request({
        url: '/product/spuImages/' + id,
        method: 'get'
    })
}

// 新增SPU图片
export function addSpuImages(data) {
    return request({
        url: '/product/spuImages',
        method: 'post',
        data: data
    })
}

// 批量新增SPU图片
export function addListSpuImages(data) {
    return request({
        url: '/product/spuImages/addList',
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
        return updateSpuImages(data);
    }
    // 修改 是否删除 状态
    export function deletedSwitchChange(id, deleted) {
        const data = {
                id,
                deleted
        }
        return updateSpuImages(data);
    }

// 修改SPU图片
export function updateSpuImages(data) {
    return request({
        url: '/product/spuImages',
        method: 'put',
        data: data
    })
}

// 批量修改SPU图片
export function updateListSpuImages(data) {
    return request({
        url: '/product/spuImages/editList',
        method: 'put',
        data: data
    })
}


// 删除SPU图片
export function delSpuImages(id) {
    return request({
        url: '/product/spuImages/' + id,
        method: 'delete'
    })
}

// 逻辑删除SPU图片
export function deleteSpuImages(data) {
    return request({
        url: '/product/spuImages/delete',
        method: 'delete',
        data: data
    })
}

// 逻辑批量删除SPU图片
export function deleteBatchSpuImages(data) {
    return request({
        url: '/product/spuImages/deleteBatch',
        method: 'delete',
        data: data
    })
}

