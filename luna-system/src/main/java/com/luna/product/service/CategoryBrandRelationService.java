package com.luna.product.service;

import java.util.*;

import com.luna.common.utils.DateUtils;
import com.luna.common.utils.StringUtils;
import com.luna.product.domain.Brand;
import com.luna.product.domain.Category;
import com.luna.product.mapper.BrandMapper;
import com.luna.product.mapper.CategoryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageInfo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import org.apache.commons.compress.utils.Lists;
import org.apache.commons.collections4.CollectionUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.luna.product.mapper.CategoryBrandRelationMapper;
import com.luna.product.domain.CategoryBrandRelation;
import com.luna.product.domain.vo.CategoryBrandRelationVO;
import com.luna.utils.DO2VOUtils;

/**
 * 品牌分类关联Service业务层处理
 *
 * @author luna
 * @date 2022-05-25
 */
@Service
public class CategoryBrandRelationService extends ServiceImpl<CategoryBrandRelationMapper, CategoryBrandRelation> {
    @Autowired
    private CategoryBrandRelationMapper categoryBrandRelationMapper;

    @Autowired
    private BrandMapper                 brandMapper;

    @Autowired
    private CategoryMapper              categoryMapper;

    /**
     * 查询品牌分类关联
     *
     * @param id 品牌分类关联主键
     * @return 品牌分类关联
     */
    public CategoryBrandRelationVO selectCategoryBrandRelationById(Long id) {
        List<CategoryBrandRelationVO> relationVOS =
            convertList(Collections.singletonList(categoryBrandRelationMapper.selectCategoryBrandRelationById(id)));

        return relationVOS.stream().findFirst().orElse(null);
    }

    /**
     * 查询品牌分类关联列表
     *
     * @param categoryBrandRelation 品牌分类关联
     * @return 品牌分类关联
     */
    public PageInfo selectCategoryBrandRelationList(CategoryBrandRelation categoryBrandRelation) {
        List<CategoryBrandRelation> list = categoryBrandRelationMapper.selectCategoryBrandRelationList(categoryBrandRelation);
        PageInfo pageInfo = new PageInfo<>(list);
        List<CategoryBrandRelationVO> resultList = convertList(list);
        pageInfo.setList(resultList);
        return pageInfo;
    }

    /**
     * 查询全部品牌分类关联列表
     *
     * @param categoryBrandRelation 品牌分类关联
     * @return 品牌分类关联
     */
    public List<CategoryBrandRelationVO> selectAllList(CategoryBrandRelation categoryBrandRelation) {

        QueryWrapper<CategoryBrandRelation> queryWrapper = new QueryWrapper<CategoryBrandRelation>(categoryBrandRelation);
        ArrayList<CategoryBrandRelationVO> list = Lists.newArrayList();
        Page<CategoryBrandRelation> of = Page.of(0, 2000);
        while (true) {
            Page<CategoryBrandRelation> categoryBrandRelationPage = categoryBrandRelationMapper.selectPage(of, queryWrapper);
            List<CategoryBrandRelationVO> relationVOS = convertList(categoryBrandRelationPage.getRecords());
            if (CollectionUtils.isEmpty(relationVOS)) {
                break;
            }
            of.setCurrent(of.getCurrent() + 1);
            list.addAll(relationVOS);
        }

        return list;
    }

    /**
     * ids查询品牌列表
     *
     * @param ids 品牌分类关联
     * @return 品牌分类关联
     */
    public List<CategoryBrandRelation> selectCategoryBrandRelationByIds(List<Long> ids) {
        if (CollectionUtils.isEmpty(ids)) {
            return new ArrayList<>();
        }
        return categoryBrandRelationMapper.selectBatchIds(ids);
    }

    /**
     * 分页查询品牌分类关联列表
     *
     * @param categoryBrandRelation 品牌分类关联
     * @param page 分页参数
     * @return 品牌分类关联
     */
    public IPage<CategoryBrandRelation> selectList(IPage<CategoryBrandRelation> page, CategoryBrandRelation categoryBrandRelation) {
        QueryWrapper<CategoryBrandRelation> queryWrapper = new QueryWrapper<CategoryBrandRelation>(categoryBrandRelation);
        for (OrderItem order : page.orders()) {
            queryWrapper.orderBy(true, order.isAsc(), order.getColumn());
        }
        Page<CategoryBrandRelation> selectPage = Page.of(page.getCurrent(), page.getSize());
        selectPage.setMaxLimit(page.maxLimit());
        return categoryBrandRelationMapper.selectPage(selectPage, queryWrapper);
    }

    /**
     * 分页查询品牌分类关联VO视图列表
     *
     * @param categoryBrandRelation 品牌分类关联
     * @param page 分页参数
     * @return 品牌分类关联
     */
    public IPage<CategoryBrandRelationVO> selectVOList(IPage<CategoryBrandRelation> page, CategoryBrandRelation categoryBrandRelation) {
        IPage<CategoryBrandRelation> categoryBrandRelationPage = selectList(page, categoryBrandRelation);
        List<CategoryBrandRelation> records = categoryBrandRelationPage.getRecords();

        List<CategoryBrandRelationVO> list = convertList(records);

        Page<CategoryBrandRelationVO> result =
            new Page<>(categoryBrandRelationPage.getCurrent(), categoryBrandRelationPage.getSize(), categoryBrandRelationPage.getTotal());
        result.setRecords(list);
        return result;
    }

    /**
     * 构造数据
     * 
     * @param records
     * @return
     */
    public List<CategoryBrandRelationVO> convertList(List<CategoryBrandRelation> records) {
        List<CategoryBrandRelationVO> list = new ArrayList<>();
        if (CollectionUtils.isEmpty(records)) {
            return list;
        }
        for (CategoryBrandRelation record : records) {
            String categoryName = Optional.ofNullable(record.getCategoryId()).map(id -> categoryMapper.selectCategoryById(id)).map(Category::getName)
                .orElse(StringUtils.EMPTY);
            String brandName = Optional.ofNullable(record.getBrandId()).map(id -> brandMapper.selectBrandById(id)).map(Brand::getName)
                .orElse(StringUtils.EMPTY);

            CategoryBrandRelationVO categoryBrandRelationVO =
                DO2VOUtils.categoryBrandRelation2CategoryBrandRelationVO(record, brandName, categoryName);
            list.add(categoryBrandRelationVO);
        }
        return list;
    }

    /**
     * 新增品牌分类关联
     *
     * @param categoryBrandRelation 品牌分类关联
     * @return 结果
     */
    public int insertCategoryBrandRelation(CategoryBrandRelation categoryBrandRelation) {
        categoryBrandRelation.setCreateTime(DateUtils.getNowDate());
        return categoryBrandRelationMapper.insertCategoryBrandRelation(categoryBrandRelation);
    }

    /**
     * 修改品牌分类关联
     *
     * @param categoryBrandRelation 品牌分类关联
     * @return 结果
     */
    public int updateCategoryBrandRelation(CategoryBrandRelation categoryBrandRelation) {
        categoryBrandRelation.setUpdateTime(DateUtils.getNowDate());
        return categoryBrandRelationMapper.updateCategoryBrandRelation(categoryBrandRelation);
    }

    /**
     * 批量修改品牌分类关联列表
     *
     * @param categoryBrandRelationList 品牌分类关联列表
     * @return 结果
     */
    public Boolean updatecategoryBrandRelationBatch(List<CategoryBrandRelation> categoryBrandRelationList) {
        if (CollectionUtils.isEmpty(categoryBrandRelationList)) {
            return true;
        }
        return updateBatchById(categoryBrandRelationList);
    }

    /**
     * 批量删除品牌分类关联
     *
     * @param ids 需要删除的品牌分类关联主键
     * @return 结果
     */
    public int deleteCategoryBrandRelationByIds(Long[] ids) {
        return categoryBrandRelationMapper.deleteCategoryBrandRelationByIds(ids);
    }

    /**
     * 批量逻辑删除品牌分类关联
     *
     * @param categoryBrandRelationIds 品牌分类关联
     * @return 结果
     */
    public int deleteIds(List<Long> categoryBrandRelationIds) {
        if (CollectionUtils.isEmpty(categoryBrandRelationIds)) {
            return 0;
        }
        QueryWrapper<CategoryBrandRelation> queryWrapper = new QueryWrapper<CategoryBrandRelation>();
        queryWrapper.in("id", categoryBrandRelationIds);
        return categoryBrandRelationMapper.delete(queryWrapper);
    }

    /**
     * 逻辑删除品牌分类关联信息
     *
     * @param categoryBrandRelation categoryBrandRelation 主键
     * @return 结果
     */
    public int delete(CategoryBrandRelation categoryBrandRelation) {
        QueryWrapper<CategoryBrandRelation> queryWrapper = new QueryWrapper<CategoryBrandRelation>(categoryBrandRelation);
        return categoryBrandRelationMapper.delete(queryWrapper);
    }
}
