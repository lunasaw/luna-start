package com.luna.product.service;

import java.util.List;
import com.luna.common.utils.DateUtils;
import com.luna.common.utils.StringUtils;
import com.luna.product.domain.Category;
import com.luna.product.domain.SpuInfo;
import com.luna.product.mapper.SpuInfoMapper;
import com.luna.utils.DO2VOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Optional;

import com.github.pagehelper.PageInfo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import org.apache.commons.compress.utils.Lists;
import org.apache.commons.collections4.CollectionUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.luna.product.mapper.SpuImagesMapper;
import com.luna.product.domain.SpuImages;
import com.luna.product.domain.vo.SpuImagesVO;

/**
 * SPU图片Service业务层处理
 *
 * @author luna
 * @date 2022-05-23
 */
@Service
public class SpuImagesService extends ServiceImpl<SpuImagesMapper, SpuImages> {
    @Autowired
    private SpuImagesMapper spuImagesMapper;

    @Autowired
    private SpuInfoMapper   spuInfoMapper;

    /**
     * 查询SPU图片
     *
     * @param id SPU图片主键
     * @return SPU图片
     */
    public SpuImages selectSpuImagesById(Long id) {
        return spuImagesMapper.selectSpuImagesById(id);
    }

    /**
     * 查询SPU图片列表
     *
     * @param spuImages SPU图片
     * @return SPU图片
     */
    public PageInfo selectSpuImagesList(SpuImages spuImages) {
        List<SpuImages> list = spuImagesMapper.selectSpuImagesList(spuImages);
        PageInfo pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    /**
     * 查询全部SPU图片列表
     *
     * @param spuImages SPU图片
     * @return SPU图片
     */
    public List<SpuImages> selectAllList(SpuImages spuImages) {

        QueryWrapper<SpuImages> queryWrapper = new QueryWrapper<SpuImages>(spuImages);
        ArrayList<SpuImages> list = Lists.newArrayList();
        Page<SpuImages> of = Page.of(0, 2000);
        while (true) {
            Page<SpuImages> spuImagesPage = spuImagesMapper.selectPage(of, queryWrapper);
            List<SpuImages> spuImagess = spuImagesPage.getRecords();
            if (CollectionUtils.isEmpty(spuImagess)) {
                break;
            }
            of.setCurrent(of.getCurrent() + 1);
            list.addAll(spuImagess);
        }

        return list;
    }

    /**
     * ids查询品牌列表
     *
     * @param ids SPU图片
     * @return SPU图片
     */
    public List<SpuImages> selectSpuImagesByIds(List<Long> ids) {
        if (CollectionUtils.isEmpty(ids)) {
            return new ArrayList<>();
        }
        return spuImagesMapper.selectBatchIds(ids);
    }

    /**
     * 分页查询SPU图片列表
     *
     * @param spuImages SPU图片
     * @param page 分页参数
     * @return SPU图片
     */
    public IPage<SpuImages> selectList(IPage<SpuImages> page, SpuImages spuImages) {
        QueryWrapper<SpuImages> queryWrapper = new QueryWrapper<SpuImages>(spuImages);
        for (OrderItem order : page.orders()) {
            queryWrapper.orderBy(true, order.isAsc(), order.getColumn());
        }
        Page<SpuImages> selectPage = Page.of(page.getCurrent(), page.getSize());
        selectPage.setMaxLimit(page.maxLimit());
        return spuImagesMapper.selectPage(selectPage, queryWrapper);
    }

    /**
     * 分页查询SPU图片VO视图列表
     *
     * @param spuImages SPU图片
     * @param page 分页参数
     * @return SPU图片
     */
    public IPage<SpuImagesVO> selectVOList(IPage<SpuImages> page, SpuImages spuImages) {
        IPage<SpuImages> spuImagesPage = selectList(page, spuImages);
        List<SpuImagesVO> list = new ArrayList<>();
        List<SpuImages> records = spuImagesPage.getRecords();

        for (SpuImages record : records) {
            String spuName = Optional.ofNullable(record.getSpuId()).map(id -> spuInfoMapper.selectSpuInfoById(id)).map(SpuInfo::getSpuName)
                .orElse(StringUtils.EMPTY);
            SpuImagesVO spuImagesVO = DO2VOUtils.spuImages2SpuImagesVO(record, spuName);
            list.add(spuImagesVO);
        }
        Page<SpuImagesVO> result = new Page<>(spuImagesPage.getCurrent(), spuImagesPage.getSize(), spuImagesPage.getTotal());
        result.setRecords(list);
        return result;
    }

    /**
     * 新增SPU图片
     *
     * @param spuImages SPU图片
     * @return 结果
     */
    public int insertSpuImages(SpuImages spuImages) {
        spuImages.setCreateTime(DateUtils.getNowDate());
        return spuImagesMapper.insertSpuImages(spuImages);
    }

    /**
     * 修改SPU图片
     *
     * @param spuImages SPU图片
     * @return 结果
     */
    public int updateSpuImages(SpuImages spuImages) {
        spuImages.setUpdateTime(DateUtils.getNowDate());
        return spuImagesMapper.updateSpuImages(spuImages);
    }

    /**
     * 批量删除SPU图片
     *
     * @param ids 需要删除的SPU图片主键
     * @return 结果
     */
    public int deleteSpuImagesByIds(Long[] ids) {
        return spuImagesMapper.deleteSpuImagesByIds(ids);
    }

    /**
     * 批量逻辑删除SPU图片
     *
     * @param spuImagesIds SPU图片
     * @return 结果
     */
    public int deleteIds(List<Long> spuImagesIds) {
        if (CollectionUtils.isEmpty(spuImagesIds)) {
            return 0;
        }
        QueryWrapper<SpuImages> queryWrapper = new QueryWrapper<SpuImages>();
        queryWrapper.in("id", spuImagesIds);
        return spuImagesMapper.delete(queryWrapper);
    }

    /**
     * 逻辑删除SPU图片信息
     *
     * @param spuImages spuImages 主键
     * @return 结果
     */
    public int delete(SpuImages spuImages) {
        QueryWrapper<SpuImages> queryWrapper = new QueryWrapper<SpuImages>(spuImages);
        return spuImagesMapper.delete(queryWrapper);
    }
}
