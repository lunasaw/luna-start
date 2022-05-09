package com.luna.product.service;

import java.util.List;
import com.luna.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import org.apache.commons.compress.utils.Lists;
import org.apache.commons.collections4.CollectionUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.luna.product.mapper.CategoryAttributeMapper;
import com.luna.product.domain.CategoryAttribute;

/**
 * 产品属性分类Service业务层处理
 *
 * @author luna
 * @date 2022-05-09
 */
@Service
public class CategoryAttributeService extends ServiceImpl<CategoryAttributeMapper, CategoryAttribute> {
    @Autowired
    private CategoryAttributeMapper categoryAttributeMapper;

    /**
     * 查询产品属性分类
     *
     * @param id 产品属性分类主键
     * @return 产品属性分类
     */
    public CategoryAttribute selectCategoryAttributeById(Long id) {
        return categoryAttributeMapper.selectCategoryAttributeById(id);
    }

    /**
     * 查询产品属性分类列表
     *
     * @param categoryAttribute 产品属性分类
     * @return 产品属性分类
     */
    public List<CategoryAttribute> selectCategoryAttributeList(CategoryAttribute categoryAttribute) {
        return categoryAttributeMapper.selectCategoryAttributeList(categoryAttribute);
    }

    /**
     * 查询全部产品属性分类列表
     *
     * @param categoryAttribute 产品属性分类
     * @return 产品属性分类
     */
    public List<CategoryAttribute> selectAllList(CategoryAttribute categoryAttribute) {

        QueryWrapper<CategoryAttribute> queryWrapper = new QueryWrapper<CategoryAttribute>(categoryAttribute);
        queryWrapper.last("limit 100");
        ArrayList<CategoryAttribute> list = Lists.newArrayList();
        while (true) {
            List<CategoryAttribute> categoryAttributes = categoryAttributeMapper.selectList(queryWrapper);
            if (CollectionUtils.isEmpty(categoryAttributes)) {
                break;
            }
            list.addAll(categoryAttributes);
        }

        return list;
    }

    /**
     * ids查询品牌列表
     *
     * @param ids 产品属性分类
     * @return 产品属性分类
     */
    public List<CategoryAttribute> selectCategoryAttributeByIds(List<Long> ids) {
        if (CollectionUtils.isEmpty(ids)) {
            return new ArrayList<>();
        }
        return categoryAttributeMapper.selectBatchIds(ids);
    }

    /**
     * 分页查询产品属性分类列表
     *
     * @param categoryAttribute 产品属性分类
     * @param page 分页参数
     * @return 产品属性分类
     */
    public IPage<CategoryAttribute> selectList(IPage<CategoryAttribute> page, CategoryAttribute categoryAttribute) {
        QueryWrapper<CategoryAttribute> queryWrapper = new QueryWrapper<CategoryAttribute>(categoryAttribute);
        for (OrderItem order : page.orders()) {
            queryWrapper.orderBy(true, order.isAsc(), order.getColumn());
        }
        Page<CategoryAttribute> selectPage = Page.of(page.getCurrent(), page.getSize());
        selectPage.setMaxLimit(page.maxLimit());
        return categoryAttributeMapper.selectPage(selectPage, queryWrapper);
    }

    /**
     * 新增产品属性分类
     *
     * @param categoryAttribute 产品属性分类
     * @return 结果
     */
    public int insertCategoryAttribute(CategoryAttribute categoryAttribute) {
        categoryAttribute.setCreateTime(DateUtils.getNowDate());
        return categoryAttributeMapper.insertCategoryAttribute(categoryAttribute);
    }

    /**
     * 修改产品属性分类
     *
     * @param categoryAttribute 产品属性分类
     * @return 结果
     */
    public int updateCategoryAttribute(CategoryAttribute categoryAttribute) {
        categoryAttribute.setUpdateTime(DateUtils.getNowDate());
        return categoryAttributeMapper.updateCategoryAttribute(categoryAttribute);
    }

    /**
     * 批量删除产品属性分类
     *
     * @param ids 需要删除的产品属性分类主键
     * @return 结果
     */
    public int deleteCategoryAttributeByIds(Long[] ids) {
        return categoryAttributeMapper.deleteCategoryAttributeByIds(ids);
    }

    /**
     * 删除产品属性分类信息
     *
     * @param id 产品属性分类主键
     * @return 结果
     */
    public int deleteCategoryAttributeById(Long id) {
        return categoryAttributeMapper.deleteCategoryAttributeById(id);
    }
}
