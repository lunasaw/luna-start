package com.luna.product.service;

import java.util.List;
import com.luna.common.utils.DateUtils;
import com.luna.product.domain.CategoryCascadeVO;
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
import com.luna.product.mapper.CategoryMapper;
import com.luna.product.domain.Category;

/**
 * 产品分类Service业务层处理
 *
 * @author luna
 * @date 2022-05-09
 */
@Service
public class CategoryService extends ServiceImpl<CategoryMapper, Category> {
    @Autowired
    private CategoryMapper categoryMapper;

    /**
     * 查询产品分类
     *
     * @param id 产品分类主键
     * @return 产品分类
     */
    public Category selectCategoryById(Long id) {
        return categoryMapper.selectCategoryById(id);
    }

    /**
     * 查询产品分类列表
     *
     * @param category 产品分类
     * @return 产品分类
     */
    public List<Category> selectCategoryList(Category category) {
        return categoryMapper.selectCategoryList(category);
    }


    /**
     * 查询全部产品分类列表
     *
     * @param category 产品分类
     * @return 产品分类
     */
    public List<Category> selectAllList(Category category) {

        QueryWrapper<Category> queryWrapper = new QueryWrapper<Category>(category);
        queryWrapper.last("limit 100");
        ArrayList<Category> list = Lists.newArrayList();
        while (true) {
            List<Category> categorys = categoryMapper.selectList(queryWrapper);
            if (CollectionUtils.isEmpty(categorys)) {
                break;
            }
            list.addAll(categorys);
        }

        return list;
    }

    /**
     * ids查询品牌列表
     *
     * @param ids 产品分类
     * @return 产品分类
     */
    public List<Category> selectCategoryByIds(List<Long> ids) {
        if (CollectionUtils.isEmpty(ids)) {
            return new ArrayList<>();
        }
        return categoryMapper.selectBatchIds(ids);
    }

    /**
     * 分页查询产品分类列表
     *
     * @param category 产品分类
     * @param page 分页参数
     * @return 产品分类
     */
    public IPage<Category> selectList(IPage<Category> page, Category category) {
        QueryWrapper<Category> queryWrapper = new QueryWrapper<Category>(category);
        for (OrderItem order : page.orders()) {
            queryWrapper.orderBy(true, order.isAsc(), order.getColumn());
        }
        Page<Category> selectPage = Page.of(page.getCurrent(), page.getSize());
        selectPage.setMaxLimit(page.maxLimit());
        return categoryMapper.selectPage(selectPage, queryWrapper);
    }


    /**
     * 新增产品分类
     *
     * @param category 产品分类
     * @return 结果
     */
    public int insertCategory(Category category) {
        category.setCreateTime(DateUtils.getNowDate());
        return categoryMapper.insertCategory(category);
    }

    /**
     * 修改产品分类
     *
     * @param category 产品分类
     * @return 结果
     */
    public int updateCategory(Category category) {
        category.setUpdateTime(DateUtils.getNowDate());
        return categoryMapper.updateCategory(category);
    }

    /**
     * 批量删除产品分类
     *
     * @param ids 需要删除的产品分类主键
     * @return 结果
     */
    public int deleteCategoryByIds(Long[] ids) {
        return categoryMapper.deleteCategoryByIds(ids);
    }

    /**
     * 删除产品分类信息
     *
     * @param id 产品分类主键
     * @return 结果
     */
    public int deleteCategoryById(Long id) {
        return categoryMapper.deleteCategoryById(id);
    }

    /**
     * 及联查询分类列表
     * 
     * @param category
     * @return
     */
    public List<CategoryCascadeVO> getCategoryCascadeVO(Category category) {
        Long categoryId = Optional.ofNullable(category.getParentId()).orElse(0L);
        category.setParentId(categoryId);
        ArrayList<CategoryCascadeVO> arrayList = Lists.newArrayList();
        List<Category> categories = categoryMapper.selectCategoryList(category);
        if (CollectionUtils.isEmpty(categories)) {
            return arrayList;
        }
        List<CategoryCascadeVO> cascadeVOList = categories.stream().map(DO2VOUtils::Category2CategoryCascadeVO).collect(Collectors.toList());
        arrayList.addAll(cascadeVOList);
        for (CategoryCascadeVO categoryTemp : cascadeVOList) {
            Category categoryChild = new Category();
            categoryChild.setParentId(categoryTemp.getId());
            List<CategoryCascadeVO> childCategory = getCategoryCascadeVO(categoryChild);
            if (CollectionUtils.isEmpty(childCategory)) {
                continue;
            }
            categoryTemp.setChildCategory(childCategory);
        }
        return arrayList;
    }
}
