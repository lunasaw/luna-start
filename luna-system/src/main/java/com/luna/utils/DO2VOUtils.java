package com.luna.utils;

import com.luna.product.domain.Category;
import com.luna.product.domain.vo.CategoryCascadeVO;
import com.luna.product.domain.vo.CategoryVO;

/**
 * @author luna
 * 2022/5/12
 */
public class DO2VOUtils {

    public static CategoryVO category2CategoryVO(Category category, String parentName) {
        if (category == null) {
            return null;
        }
        CategoryVO categoryVO = new CategoryVO();
        categoryVO.setId(category.getId());
        categoryVO.setParentId(category.getParentId());
        categoryVO.setParentName(parentName);
        categoryVO.setName(category.getName());
        categoryVO.setLevel(category.getLevel());
        categoryVO.setProductCount(category.getProductCount());
        categoryVO.setProductUnit(category.getProductUnit());
        categoryVO.setNavStatus(category.getNavStatus());
        categoryVO.setShowStatus(category.getShowStatus());
        categoryVO.setSort(category.getSort());
        categoryVO.setIcon(category.getIcon());
        categoryVO.setKeywords(category.getKeywords());
        categoryVO.setDescription(category.getDescription());
        categoryVO.setSearchValue(category.getSearchValue());
        categoryVO.setCreateBy(category.getCreateBy());
        categoryVO.setCreateTime(category.getCreateTime());
        categoryVO.setDeleted(category.getDeleted());
        categoryVO.setUpdateBy(category.getUpdateBy());
        categoryVO.setUpdateTime(category.getUpdateTime());
        categoryVO.setRemark(category.getRemark());
        categoryVO.setParams(category.getParams());
        return categoryVO;
    }
    public static CategoryCascadeVO Category2CategoryCascadeVO(Category category) {
        if (category == null) {
            return null;
        }
        CategoryCascadeVO categoryCascadeVO = new CategoryCascadeVO();
        categoryCascadeVO.setId(category.getId());
        categoryCascadeVO.setName(category.getName());
        categoryCascadeVO.setLevel(category.getLevel());
        categoryCascadeVO.setProductCount(category.getProductCount());
        categoryCascadeVO.setProductUnit(category.getProductUnit());
        categoryCascadeVO.setNavStatus(category.getNavStatus());
        categoryCascadeVO.setShowStatus(category.getShowStatus());
        categoryCascadeVO.setSort(category.getSort());
        categoryCascadeVO.setIcon(category.getIcon());
        categoryCascadeVO.setKeywords(category.getKeywords());
        categoryCascadeVO.setDescription(category.getDescription());
        categoryCascadeVO.setSearchValue(category.getSearchValue());
        categoryCascadeVO.setCreateBy(category.getCreateBy());
        categoryCascadeVO.setCreateTime(category.getCreateTime());
        categoryCascadeVO.setDeleted(category.getDeleted());
        categoryCascadeVO.setUpdateBy(category.getUpdateBy());
        categoryCascadeVO.setUpdateTime(category.getUpdateTime());
        categoryCascadeVO.setRemark(category.getRemark());
        categoryCascadeVO.setParams(category.getParams());
        return categoryCascadeVO;
    }
}
