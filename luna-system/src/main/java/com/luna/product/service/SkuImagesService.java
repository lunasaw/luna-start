package com.luna.product.service;

import java.util.List;
import com.luna.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import com.github.pagehelper.PageInfo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import org.apache.commons.compress.utils.Lists;
import org.apache.commons.collections4.CollectionUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.luna.product.mapper.SkuImagesMapper;
import com.luna.product.domain.SkuImages;
import com.luna.product.domain.vo.SkuImagesVO;
import com.luna.product.utils.DO2VOUtils;

/**
 * sku图片Service业务层处理
 *
 * @author luna
 * @date 2022-05-31
 */
@Service
public class SkuImagesService extends ServiceImpl<SkuImagesMapper, SkuImages> {
    @Autowired
    private SkuImagesMapper skuImagesMapper;

    /**
     * 查询sku图片
     *
     * @param id sku图片主键
     * @return sku图片
     */
    public SkuImages selectSkuImagesById(Long id) {
        return skuImagesMapper.selectSkuImagesById(id);
    }

    /**
     * 查询sku图片列表
     *
     * @param skuImages sku图片
     * @return sku图片
     */
    public PageInfo selectSkuImagesList(SkuImages skuImages) {
        List<SkuImages> list = skuImagesMapper.selectSkuImagesList(skuImages);
        PageInfo pageInfo = new PageInfo<>(list);
        List<SkuImagesVO> resultList = convertList(list);
        pageInfo.setList(resultList);
        return pageInfo;
    }


    /**
     * 查询全部sku图片列表
     *
     * @param skuImages sku图片
     * @return sku图片
     */
    public List<SkuImagesVO> selectAllList(SkuImages skuImages) {

        QueryWrapper<SkuImages> queryWrapper = new QueryWrapper<SkuImages>(skuImages);
        ArrayList<SkuImagesVO> list = Lists.newArrayList();
        Page<SkuImages> of = Page.of(0, 2000);
        while (true) {
            Page<SkuImages> skuImagesPage = skuImagesMapper.selectPage(of, queryWrapper);
            List<SkuImagesVO> resultList = convertList(skuImagesPage.getRecords());
            if (CollectionUtils.isEmpty(resultList)) {
                break;
            }
            of.setCurrent(of.getCurrent() + 1);
            list.addAll(resultList);
        }

        return list;
    }

    /**
    * ids查询品牌列表
    *
     * @param ids sku图片
     * @return sku图片
    */
    public List<SkuImages> selectSkuImagesByIds(List<Long> ids){
        if (CollectionUtils.isEmpty(ids)){
            return new ArrayList<>();
        }
        return skuImagesMapper.selectBatchIds(ids);
    }

    /**
     * 分页查询sku图片列表
     *
     * @param skuImages sku图片
     * @param page 分页参数
     * @return sku图片
     */
    public IPage<SkuImages> selectList(IPage<SkuImages> page, SkuImages skuImages) {
        QueryWrapper<SkuImages> queryWrapper = new QueryWrapper<SkuImages>(skuImages);
        for (OrderItem order : page.orders()) {
            queryWrapper.orderBy(true, order.isAsc(), order.getColumn());
        }
        Page<SkuImages> selectPage = Page.of(page.getCurrent(), page.getSize());
        selectPage.setMaxLimit(page.maxLimit());
        return skuImagesMapper.selectPage(selectPage, queryWrapper);
    }

    /**
     * 分页查询sku图片VO视图列表
     *
     * @param skuImages sku图片
     * @param page 分页参数
     * @return sku图片
     */
    public IPage<SkuImagesVO> selectVOList(IPage<SkuImages> page, SkuImages skuImages) {
        IPage<SkuImages> skuImagesPage = selectList(page, skuImages);
        List<SkuImages> records = skuImagesPage.getRecords();

        List<SkuImagesVO> list = convertList(records);

        Page<SkuImagesVO> result = new Page<>(skuImagesPage.getCurrent(), skuImagesPage.getSize(), skuImagesPage.getTotal());
        result.setRecords(list);
        return result;
    }

    /**
    * 构造数据
    * @param records
    * @return
    */
    public List<SkuImagesVO> convertList(List<SkuImages> records) {
        List<SkuImagesVO> list = new ArrayList<>();
        if (CollectionUtils.isEmpty(records)) {
            return list;
        }
        for (SkuImages record:records) {
            SkuImagesVO skuImagesVO = DO2VOUtils .skuImages2SkuImagesVO(record);
            list.add(skuImagesVO);
        }
        return list;
    }


    /**
     * 新增sku图片
     *
     * @param skuImages sku图片
     * @return 结果
     */
    public int insertSkuImages(SkuImages skuImages) {
                skuImages.setCreateTime(DateUtils.getNowDate());
            return skuImagesMapper.insertSkuImages(skuImages);
    }

    /**
     * 修改sku图片
     *
     * @param skuImages sku图片
     * @return 结果
     */
    public int updateSkuImages(SkuImages skuImages) {
                skuImages.setUpdateTime(DateUtils.getNowDate());
        return skuImagesMapper.updateSkuImages(skuImages);
    }

    /**
     * 批量修改sku图片列表
     *
     * @param skuImagesList sku图片列表
     * @return 结果
     */
    public Boolean updateskuImagesBatch(List<SkuImages> skuImagesList) {
        if (CollectionUtils.isEmpty(skuImagesList)){
            return true;
        }
        return updateBatchById(skuImagesList);
    }

    /**
     * 批量删除sku图片
     *
     * @param ids 需要删除的sku图片主键
     * @return 结果
     */
    public int deleteSkuImagesByIds(Long[] ids) {
        return skuImagesMapper.deleteSkuImagesByIds(ids);
    }

    /**
     * 批量逻辑删除sku图片
     *
     * @param skuImagesIds sku图片
     * @return 结果
     */
    public int deleteIds(List<Long> skuImagesIds) {
        if (CollectionUtils.isEmpty(skuImagesIds)) {
            return 0;
        }
        QueryWrapper<SkuImages> queryWrapper = new QueryWrapper<SkuImages>();
        queryWrapper.in("id", skuImagesIds);
        return skuImagesMapper.delete(queryWrapper);
    }


    /**
     * 逻辑删除sku图片信息
     *
     * @param skuImages skuImages 主键
     * @return 结果
     */
    public int delete(SkuImages skuImages) {
        QueryWrapper<SkuImages> queryWrapper = new QueryWrapper<SkuImages>(skuImages);
        return skuImagesMapper.delete(queryWrapper);
    }
}
