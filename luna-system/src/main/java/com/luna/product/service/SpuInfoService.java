package com.luna.product.service;

import java.util.List;
import com.luna.common.utils.DateUtils;
import com.luna.common.utils.StringUtils;
import com.luna.product.domain.Brand;
import com.luna.product.domain.Category;
import com.luna.product.mapper.BrandMapper;
import com.luna.product.mapper.CategoryMapper;
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
import com.luna.product.mapper.SpuInfoMapper;
import com.luna.product.domain.SpuInfo;
import com.luna.product.domain.vo.SpuInfoVO;

/**
 * 商品SPU信息Service业务层处理
 *
 * @author luna
 * @date 2022-05-23
 */
@Service
public class SpuInfoService extends ServiceImpl<SpuInfoMapper, SpuInfo> {
    @Autowired
    private SpuInfoMapper  spuInfoMapper;

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private BrandMapper    brandMapper;

    /**
     * 查询商品SPU信息
     *
     * @param id 商品SPU信息主键
     * @return 商品SPU信息
     */
    public SpuInfo selectSpuInfoById(Long id) {
        return spuInfoMapper.selectSpuInfoById(id);
    }

    /**
     * 查询商品SPU信息列表
     *
     * @param spuInfo 商品SPU信息
     * @return 商品SPU信息
     */
    public PageInfo selectSpuInfoList(SpuInfo spuInfo) {
        List<SpuInfo> list = spuInfoMapper.selectSpuInfoList(spuInfo);
        PageInfo pageInfo = new PageInfo<>(list);
        return pageInfo;
    }


    /**
     * 查询全部商品SPU信息列表
     *
     * @param spuInfo 商品SPU信息
     * @return 商品SPU信息
     */
    public List<SpuInfo> selectAllList(SpuInfo spuInfo) {

        QueryWrapper<SpuInfo> queryWrapper = new QueryWrapper<SpuInfo>(spuInfo);
        ArrayList<SpuInfo> list = Lists.newArrayList();
        Page<SpuInfo> of = Page.of(0, 2000);
        while (true) {
            Page<SpuInfo> spuInfoPage = spuInfoMapper.selectPage(of, queryWrapper);
            List<SpuInfo> spuInfos = spuInfoPage.getRecords();
            if (CollectionUtils.isEmpty(spuInfos)) {
                break;
            }
            of.setCurrent(of.getCurrent() + 1);
            list.addAll(spuInfos);
        }

        return list;
    }

    /**
     * ids查询品牌列表
     *
     * @param ids 商品SPU信息
     * @return 商品SPU信息
     */
    public List<SpuInfo> selectSpuInfoByIds(List<Long> ids) {
        if (CollectionUtils.isEmpty(ids)) {
            return new ArrayList<>();
        }
        return spuInfoMapper.selectBatchIds(ids);
    }

    /**
     * 分页查询商品SPU信息列表
     *
     * @param spuInfo 商品SPU信息
     * @param page 分页参数
     * @return 商品SPU信息
     */
    public IPage<SpuInfo> selectList(IPage<SpuInfo> page, SpuInfo spuInfo) {
        QueryWrapper<SpuInfo> queryWrapper = new QueryWrapper<SpuInfo>(spuInfo);
        for (OrderItem order : page.orders()) {
            queryWrapper.orderBy(true, order.isAsc(), order.getColumn());
        }
        Page<SpuInfo> selectPage = Page.of(page.getCurrent(), page.getSize());
        selectPage.setMaxLimit(page.maxLimit());
        return spuInfoMapper.selectPage(selectPage, queryWrapper);
    }

    /**
     * 分页查询商品SPU信息VO视图列表
     *
     * @param spuInfo 商品SPU信息
     * @param page 分页参数
     * @return 商品SPU信息
     */
    public IPage<SpuInfoVO> selectVOList(IPage<SpuInfo> page, SpuInfo spuInfo) {
        IPage<SpuInfo> spuInfoPage = selectList(page, spuInfo);
        List<SpuInfoVO> list = new ArrayList<>();
        List<SpuInfo> records = spuInfoPage.getRecords();

        for (SpuInfo record : records) {
            String categoryName = Optional.ofNullable(record.getCatalogId()).map(id -> categoryMapper.selectCategoryById(id)).map(Category::getName)
                .orElse(StringUtils.EMPTY);
            String brandName =
                Optional.ofNullable(record.getBrandId()).map(id -> brandMapper.selectById(id)).map(Brand::getName).orElse(StringUtils.EMPTY);
            SpuInfoVO spuInfoVO = DO2VOUtils.spuInfo2SpuInfoVO(record, categoryName, brandName);
            list.add(spuInfoVO);
        }
        Page<SpuInfoVO> result = new Page<>(spuInfoPage.getCurrent(), spuInfoPage.getSize(), spuInfoPage.getTotal());
        result.setRecords(list);
        return result;
    }

    /**
     * 新增商品SPU信息
     *
     * @param spuInfo 商品SPU信息
     * @return 结果
     */
    public int insertSpuInfo(SpuInfo spuInfo) {
        spuInfo.setCreateTime(DateUtils.getNowDate());
        return spuInfoMapper.insertSpuInfo(spuInfo);
    }

    /**
     * 修改商品SPU信息
     *
     * @param spuInfo 商品SPU信息
     * @return 结果
     */
    public int updateSpuInfo(SpuInfo spuInfo) {
        spuInfo.setUpdateTime(DateUtils.getNowDate());
        return spuInfoMapper.updateSpuInfo(spuInfo);
    }

    /**
     * 批量删除商品SPU信息
     *
     * @param ids 需要删除的商品SPU信息主键
     * @return 结果
     */
    public int deleteSpuInfoByIds(Long[] ids) {
        return spuInfoMapper.deleteSpuInfoByIds(ids);
    }

    /**
     * 批量逻辑删除商品SPU信息
     *
     * @param spuInfoIds 商品SPU信息
     * @return 结果
     */
    public int deleteIds(List<Long> spuInfoIds) {
        if (CollectionUtils.isEmpty(spuInfoIds)) {
            return 0;
        }
        QueryWrapper<SpuInfo> queryWrapper = new QueryWrapper<SpuInfo>();
        queryWrapper.in("id", spuInfoIds);
        return spuInfoMapper.delete(queryWrapper);
    }


    /**
     * 逻辑删除商品SPU信息信息
     *
     * @param spuInfo spuInfo 主键
     * @return 结果
     */
    public int delete(SpuInfo spuInfo) {
        QueryWrapper<SpuInfo> queryWrapper = new QueryWrapper<SpuInfo>(spuInfo);
        return spuInfoMapper.delete(queryWrapper);
    }
}
