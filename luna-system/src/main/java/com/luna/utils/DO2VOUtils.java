package com.luna.utils;

import com.luna.product.domain.Category;
import com.luna.product.domain.CategoryCascadeVO;

/**
 * @author luna
 * 2022/5/12
 */
public class DO2VOUtils {

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
