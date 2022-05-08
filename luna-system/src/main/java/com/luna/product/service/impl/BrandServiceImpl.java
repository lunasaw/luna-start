package com.luna.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.luna.product.domain.Brand;
import com.luna.product.mapper.BrandMapper;
import com.luna.product.service.IBrandService;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.compress.utils.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 品牌Service业务层处理
 * 
 * @author luna
 * @date 2022-05-01
 */
@Service
public class BrandServiceImpl extends ServiceImpl<BrandMapper, Brand> implements IBrandService {
    @Autowired
    private BrandMapper brandMapper;

    /**
     * 查询品牌
     * 
     * @param id 品牌主键
     * @return 品牌
     */
    @Override
    public Brand selectBrandById(Long id) {
        return brandMapper.selectBrandById(id);
    }

    /**
     * ids查询品牌列表
     *
     * @param ids 品牌
     * @return 品牌
     */
    @Override
    public List<Brand> selectBrandByIds(List<Long> ids) {
        if (CollectionUtils.isEmpty(ids)) {
            return new ArrayList<>();
        }
        return brandMapper.selectBatchIds(ids);
    }

    /**
     * 查询全部品牌列表
     * 
     * @param brand 品牌
     * @return 品牌
     */
    @Override
    public List<Brand> selectBrandList(Brand brand) {
        QueryWrapper<Brand> queryWrapper = new QueryWrapper<Brand>(brand);
        queryWrapper.last("limit 100");
        ArrayList<Brand> list = Lists.newArrayList();
        while (true) {
            List<Brand> brands = brandMapper.selectList(queryWrapper);
            if (CollectionUtils.isEmpty(brands)) {
                break;
            }
            list.addAll(brands);
        }
        return list;
    }

    /**
     * 查询全部品牌列表
     *
     * @param brand 品牌
     * @return 品牌
     */
    @Override
    public List<Brand> selectList(Brand brand) {
        return brandMapper.selectBrandList(brand);
    }

    /**
     * 分页查询品牌列表
     *
     * @param brand 品牌
     * @return 品牌
     */
    @Override
    public IPage<Brand> selectList(IPage<Brand> page, Brand brand) {
        QueryWrapper<Brand> queryWrapper = new QueryWrapper<Brand>(brand);
        for (OrderItem order : page.orders()) {
            queryWrapper.orderBy(true, order.isAsc(), order.getColumn());
        }
        Page<Brand> selectPage = Page.of(page.getCurrent(), page.getSize());
        selectPage.setMaxLimit(page.maxLimit());
        return brandMapper.selectPage(selectPage, queryWrapper);
    }

    /**
     * 新增品牌
     * 
     * @param brand 品牌
     * @return 结果
     */
    @Override
    public int insertBrand(Brand brand) {
        return brandMapper.insertBrand(brand);
    }

    /**
     * 修改品牌
     * 
     * @param brand 品牌
     * @return 结果
     */
    @Override
    public int updateBrand(Brand brand) {
        return brandMapper.updateBrand(brand);
    }

    /**
     * 批量删除品牌
     * 
     * @param ids 需要删除的品牌主键
     * @return 结果
     */
    @Override
    public int deleteBrandByIds(Long[] ids) {
        return brandMapper.deleteBrandByIds(ids);
    }

    /**
     * 删除品牌信息
     * 
     * @param id 品牌主键
     * @return 结果
     */
    @Override
    public int deleteBrandById(Long id) {
        return brandMapper.deleteBrandById(id);
    }
}
