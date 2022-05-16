package com.luna.product.service;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.luna.common.utils.DateUtils;
import com.luna.common.utils.PageUtils;
import com.luna.common.utils.StringUtils;
import com.luna.product.domain.Category;
import com.luna.product.domain.vo.AttributeCategoryVO;
import com.luna.product.domain.vo.CategoryVO;
import com.luna.product.mapper.CategoryMapper;
import com.luna.utils.DO2VOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import org.apache.commons.compress.utils.Lists;
import org.apache.commons.collections4.CollectionUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.luna.product.mapper.AttributeCategoryMapper;
import com.luna.product.domain.AttributeCategory;

/**
 * 产品属性分类Service业务层处理
 *
 * @author luna
 * @date 2022-05-15
 */
@Service
public class AttributeCategoryService extends ServiceImpl<AttributeCategoryMapper, AttributeCategory> {
    @Autowired
    private AttributeCategoryMapper attributeCategoryMapper;

    @Autowired
    private CategoryMapper          categoryMapper;

    /**
     * 查询产品属性分类
     *
     * @param id 产品属性分类主键
     * @return 产品属性分类
     */
    public AttributeCategory selectAttributeCategoryById(Long id) {
        return attributeCategoryMapper.selectAttributeCategoryById(id);
    }

    /**
     * 查询产品属性分类列表
     *
     * @param attributeCategory 产品属性分类
     * @return 产品属性分类
     */
    public PageInfo selectAttributeCategoryList(AttributeCategory attributeCategory) {
        List<AttributeCategory> attributeCategoryList = attributeCategoryMapper.selectAttributeCategoryList(attributeCategory);
        PageInfo pageInfo = new PageInfo<>(attributeCategoryList);
        List<AttributeCategoryVO> collect = attributeCategoryList.stream().map(this::fillData).collect(Collectors.toList());
        pageInfo.setList(collect);
        return pageInfo;
    }

    public AttributeCategoryVO fillData(AttributeCategory category) {
        String categoryName =
            Optional.ofNullable(categoryMapper.selectCategoryById(category.getCategoryId())).map(Category::getName).orElse(StringUtils.EMPTY);
        AttributeCategoryVO attributeCategoryVO = DO2VOUtils.attribateCategory2AttributeCategoryVO(category, categoryName);
        return attributeCategoryVO;
    }

    /**
     * 查询全部产品属性分类列表
     *
     * @param attributeCategory 产品属性分类
     * @return 产品属性分类
     */
    public List<AttributeCategory> selectAllList(AttributeCategory attributeCategory) {

        QueryWrapper<AttributeCategory> queryWrapper = new QueryWrapper<AttributeCategory>(attributeCategory);
        ArrayList<AttributeCategory> list = Lists.newArrayList();
        Page<AttributeCategory> of = Page.of(0, 500);
        while (true) {
            Page<AttributeCategory> categoryPage = attributeCategoryMapper.selectPage(of, queryWrapper);
            List<AttributeCategory> categorys = categoryPage.getRecords();
            if (CollectionUtils.isEmpty(categorys)) {
                break;
            }
            of.setCurrent(of.getCurrent() + 1);
            list.addAll(categorys);
        }

        return list;
    }

    /**
     * ids查询品牌列表
     *
     * @param ids 产品属性分类
     * @return 产品属性分类
     */
    public List<AttributeCategory> selectAttributeCategoryByIds(List<Long> ids) {
        if (CollectionUtils.isEmpty(ids)) {
            return new ArrayList<>();
        }
        return attributeCategoryMapper.selectBatchIds(ids);
    }

    /**
     * 分页查询产品属性分类列表
     *
     * @param attributeCategory 产品属性分类
     * @param page 分页参数
     * @return 产品属性分类
     */
    public IPage<AttributeCategory> selectList(IPage<AttributeCategory> page, AttributeCategory attributeCategory) {
        QueryWrapper<AttributeCategory> queryWrapper = new QueryWrapper<AttributeCategory>(attributeCategory);
        for (OrderItem order : page.orders()) {
            queryWrapper.orderBy(true, order.isAsc(), order.getColumn());
        }
        Page<AttributeCategory> selectPage = Page.of(page.getCurrent(), page.getSize());
        selectPage.setMaxLimit(page.maxLimit());
        return attributeCategoryMapper.selectPage(selectPage, queryWrapper);
    }

    /**
     * 分页查询产品属性分类列表
     *
     * @param attributeCategory 产品属性分类
     * @param page 分页参数
     * @return 产品属性分类
     */
    public IPage<AttributeCategoryVO> selectVOList(IPage<AttributeCategory> page, AttributeCategory attributeCategory) {
        IPage<AttributeCategory> categoryPage = selectList(page, attributeCategory);

        List<AttributeCategoryVO> list = new ArrayList<>();
        List<AttributeCategory> records = categoryPage.getRecords();
        for (AttributeCategory record : records) {
            String categoryName =
                Optional.ofNullable(categoryMapper.selectCategoryById(record.getCategoryId())).map(Category::getName).orElse(StringUtils.EMPTY);
            AttributeCategoryVO attributeCategoryVO = DO2VOUtils.attribateCategory2AttributeCategoryVO(record, categoryName);
            list.add(attributeCategoryVO);
        }
        Page<AttributeCategoryVO> result = new Page<>(categoryPage.getCurrent(), categoryPage.getSize(), categoryPage.getTotal());
        result.setRecords(list);
        return result;
    }

    /**
     * 新增产品属性分类
     *
     * @param attributeCategory 产品属性分类
     * @return 结果
     */
    public int insertAttributeCategory(AttributeCategory attributeCategory) {
        attributeCategory.setCreateTime(DateUtils.getNowDate());
        return attributeCategoryMapper.insertAttributeCategory(attributeCategory);
    }

    /**
     * 修改产品属性分类
     *
     * @param attributeCategory 产品属性分类
     * @return 结果
     */
    public int updateAttributeCategory(AttributeCategory attributeCategory) {
        attributeCategory.setUpdateTime(DateUtils.getNowDate());
        return attributeCategoryMapper.updateAttributeCategory(attributeCategory);
    }

    /**
     * 批量删除产品属性分类
     *
     * @param ids 需要删除的产品属性分类主键
     * @return 结果
     */
    public int deleteAttributeCategoryByIds(Long[] ids) {
        return attributeCategoryMapper.deleteAttributeCategoryByIds(ids);
    }

    /**
     * 逻辑删除产品属性分类信息
     *
     * @param attributeCategory attributeCategory主键
     * @return 结果
     */
    public int deleteById(AttributeCategory attributeCategory) {
        QueryWrapper<AttributeCategory> queryWrapper = new QueryWrapper<AttributeCategory>(attributeCategory);
        return attributeCategoryMapper.deleteById(queryWrapper);
    }
}
