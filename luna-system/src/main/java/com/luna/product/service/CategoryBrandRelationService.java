package com.luna.product.service;

import java.util.List;
import com.luna.common.utils.DateUtils;
import com.luna.product.domain.vo.SpuInfoVO;
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
import com.luna.product.mapper.CategoryBrandRelationMapper;
import com.luna.product.domain.CategoryBrandRelation;
import com.luna.product.domain.vo.CategoryBrandRelationVO;
import com.luna.product.utils.DO2VOUtils;

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

    /**
     * 查询品牌分类关联
     *
     * @param id 品牌分类关联主键
     * @return 品牌分类关联
     */
    public CategoryBrandRelation selectCategoryBrandRelationById(Long id) {
        return categoryBrandRelationMapper.selectCategoryBrandRelationById(id);
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
    public List<CategoryBrandRelation> selectAllList(CategoryBrandRelation categoryBrandRelation) {

        QueryWrapper<CategoryBrandRelation> queryWrapper = new QueryWrapper<CategoryBrandRelation>(categoryBrandRelation);
        ArrayList<CategoryBrandRelation> list = Lists.newArrayList();
        Page<CategoryBrandRelation> of = Page.of(0, 2000);
        while (true) {
            Page<CategoryBrandRelation> categoryBrandRelationPage = categoryBrandRelationMapper.selectPage(of, queryWrapper);
            List<CategoryBrandRelation> categoryBrandRelations = categoryBrandRelationPage.getRecords();
            if (CollectionUtils.isEmpty(categoryBrandRelations)) {
                break;
            }
            of.setCurrent(of.getCurrent() + 1);
            list.addAll(categoryBrandRelations);
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
            CategoryBrandRelationVO categoryBrandRelationVO = DO2VOUtils.categoryBrandRelation2CategoryBrandRelationVO(record);
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
