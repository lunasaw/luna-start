package com.luna.product.service;

import java.util.*;

import com.github.pagehelper.PageInfo;
import com.luna.common.annotation.DataSource;
import com.luna.common.enums.DataSourceType;
import com.luna.common.utils.DateUtils;
import com.luna.common.utils.PageUtils;
import com.luna.common.utils.StringUtils;
import com.luna.product.domain.vo.CategoryCascadeVO;
import com.luna.product.domain.vo.CategoryVO;
import com.luna.utils.DO2VOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public PageInfo selectCategoryList(Category category) {
        PageUtils.startPage();
        List<Category> categories = categoryMapper.selectCategoryList(category);
        PageInfo pageInfo = new PageInfo<>(categories);
        List<CategoryVO> categoryVOS = categories.stream().map(record -> {
            String parentName =
                Optional.ofNullable(categoryMapper.selectCategoryById(record.getParentId())).map(Category::getName).orElse(StringUtils.EMPTY);
            return DO2VOUtils.category2CategoryVO(record, parentName);
        }).collect(Collectors.toList());
        pageInfo.setList(categoryVOS);
        return pageInfo;
    }

    /**
     * 查询全部产品分类列表
     *
     * @param category 产品分类
     * @return 产品分类
     */
    public List<Category> selectAllList(Category category) {

        QueryWrapper<Category> queryWrapper = new QueryWrapper<Category>(category);
        ArrayList<Category> list = Lists.newArrayList();
        Page<Category> of = Page.of(0, 2000);
        while (true) {
            Page<Category> categoryPage = categoryMapper.selectPage(of, queryWrapper);
            List<Category> categorys = categoryPage.getRecords();
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
    @DataSource(DataSourceType.SLAVE)
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
     * 分页查询产品分类列表
     *
     * @param category 产品分类
     * @param page 分页参数
     * @return 产品分类
     */
    public IPage<CategoryVO> selectVOList(IPage<Category> page, Category category) {
        IPage<Category> categoryPage = selectList(page, category);

        List<CategoryVO> list = new ArrayList<>();
        List<Category> records = categoryPage.getRecords();
        for (Category record : records) {
            String parentName =
                Optional.ofNullable(categoryMapper.selectCategoryById(record.getParentId())).map(Category::getName).orElse(StringUtils.EMPTY);
            CategoryVO categoryVO = DO2VOUtils.category2CategoryVO(record, parentName);
            list.add(categoryVO);
        }
        Page<CategoryVO> result = new Page<>(categoryPage.getCurrent(), categoryPage.getSize(), categoryPage.getTotal());
        result.setRecords(list);
        return result;
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
     * 逻辑删除产品分类信息
     *
     * @param category 产品分类主键
     * @return 结果
     */
    public int deleteCategoryById(Category category) {
        QueryWrapper<Category> queryWrapper = new QueryWrapper<Category>(category);
        return categoryMapper.deleteById(queryWrapper);
    }

    /**
     * 及联查询分类列表
     *
     * @param category
     * @return
     */
    public List<CategoryCascadeVO> getMapCategoryCascadeVO(Category category) {
        // 查询所有
        List<Category> categories = selectAllList(category);

        // 利用Map的key不重复作为父级ID
        Map<Long, List<Category>> listMap = initMap(categories);

        return getThirdCategoryCascadeVO(listMap);
    }

    public List<CategoryCascadeVO> getSecondCategoryCascadeVO(Map<Long, List<Category>> listMap) {
        List<Category> categories = listMap.get(0L);
        List<CategoryCascadeVO> cascadeVOList = categories.stream().map(DO2VOUtils::category2CategoryCascadeVO).collect(Collectors.toList());

        for (CategoryCascadeVO categoryCascadeVO : cascadeVOList) {
            List<CategoryCascadeVO> cascadeVOS = listMap.get(categoryCascadeVO.getId()).stream().map(DO2VOUtils::category2CategoryCascadeVO)
                .sorted(Comparator.comparing(CategoryCascadeVO::getSort, Comparator.nullsLast(Long::compareTo))).collect(Collectors.toList());
            categoryCascadeVO.setChildCategory(cascadeVOS);
        }

        return cascadeVOList;
    }

    public List<CategoryCascadeVO> getThirdCategoryCascadeVO(Map<Long, List<Category>> listMap) {
        List<CategoryCascadeVO> cascadeVOList = getSecondCategoryCascadeVO(listMap);

        for (CategoryCascadeVO categoryCascadeVO : cascadeVOList) {
            if (CollectionUtils.isEmpty(categoryCascadeVO.getChildCategory())) {
                continue;
            }
            List<CategoryCascadeVO> cascadeVOS = listMap.get(categoryCascadeVO.getId()).stream().map(DO2VOUtils::category2CategoryCascadeVO)
                .sorted(Comparator.comparing(CategoryCascadeVO::getSort, Comparator.nullsLast(Long::compareTo))).collect(Collectors.toList());
            categoryCascadeVO.setChildCategory(cascadeVOS);
        }

        return cascadeVOList;
    }

    public Map<Long, List<Category>> initMap(List<Category> categories) {
        Map<Long, List<Category>> map = new HashMap<>();
        // 进行封装
        for (Category category : categories) {
            Long key = category.getParentId();
            if (map.containsKey(key)) {
                // 存在
                List<Category> list = map.get(key);
                list.add(category);
            } else {
                // 不存在
                List<Category> list = new ArrayList<>();
                list.add(category);
                map.put(key, list);
            }
        }
        return map;
    }

    /**
     * 及联查询分类列表
     *
     * @param category
     * @return
     */
    public List<CategoryCascadeVO> getBestCategoryCascadeVO(Category category) {
        Long categoryId = Optional.ofNullable(category.getParentId()).orElse(0L);
        Category categoryTemp = selectCategoryById(categoryId);
        Integer level = Optional.ofNullable(categoryTemp).map(Category::getLevel).orElse(1);
        // 查零级
        category.setLevel(level);
        List<Category> categories = selectAllList(category);
        List<CategoryCascadeVO> cascadeVOList = categories.stream().map(DO2VOUtils::category2CategoryCascadeVO).collect(Collectors.toList());

        // 查一级
        category.setLevel(level + 1);
        List<Category> categoriesLevelOne = selectAllList(category);
        for (CategoryCascadeVO categoryCascadeVO : cascadeVOList) {
            List<CategoryCascadeVO> collect = categoriesLevelOne.stream().filter(e -> e.getParentId().equals(categoryCascadeVO.getId()))
                .map(DO2VOUtils::category2CategoryCascadeVO)
                .sorted(Comparator.comparing(CategoryCascadeVO::getSort, Comparator.nullsLast(Long::compareTo))).collect(Collectors.toList());
            categoryCascadeVO.setChildCategory(collect);
        }
        // 查二级
        category.setLevel(level + 2);
        List<Category> categoriesLevelTwo = selectAllList(category);
        for (CategoryCascadeVO categoryCascadeVO : cascadeVOList) {
            for (CategoryCascadeVO cascadeVO : categoryCascadeVO.getChildCategory()) {
                List<CategoryCascadeVO> collect = categoriesLevelTwo.stream().filter(e -> e.getParentId().equals(cascadeVO.getId()))
                    .map(DO2VOUtils::category2CategoryCascadeVO)
                    .sorted(Comparator.comparing(CategoryCascadeVO::getSort, Comparator.nullsLast(Long::compareTo))).collect(Collectors.toList());
                cascadeVO.setChildCategory(collect);
            }
        }
        return cascadeVOList;
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
        List<Category> categories = selectAllList(category);
        if (CollectionUtils.isEmpty(categories)) {
            return arrayList;
        }
        List<CategoryCascadeVO> cascadeVOList = categories.stream().map(DO2VOUtils::category2CategoryCascadeVO).collect(Collectors.toList());
        arrayList.addAll(cascadeVOList);
        for (CategoryCascadeVO categoryTemp : cascadeVOList) {
            Category categoryChild = new Category();
            categoryChild.setParentId(categoryTemp.getId());
            List<CategoryCascadeVO> childCategory = getCategoryCascadeVO(categoryChild);
            if (CollectionUtils.isEmpty(childCategory)) {
                continue;
            }
            // 排序
            childCategory =
                childCategory.stream().sorted(Comparator.comparing(CategoryCascadeVO::getSort, Comparator.nullsLast(Long::compareTo)))
                    .collect(Collectors.toList());
            categoryTemp.setChildCategory(childCategory);
        }
        return arrayList;
    }
}
