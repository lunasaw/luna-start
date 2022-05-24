package com.luna.utils;

import com.luna.product.domain.Attribute;
import com.luna.product.domain.AttributeCategory;
import com.luna.product.domain.Category;
import com.luna.product.domain.SpuInfo;
import com.luna.product.domain.vo.*;

/**
 * @author luna
 * 2022/5/12
 */
public class DO2VOUtils {

    public static SpuInfoVO spuInfo2SpuInfoVO(SpuInfo SpuInfo, String categoryName, String brandName) {
        if (SpuInfo == null) {
            return null;
        }

        SpuInfoVO spuInfoVO = new SpuInfoVO();
        spuInfoVO.setId(SpuInfo.getId());
        spuInfoVO.setSpuName(SpuInfo.getSpuName());
        spuInfoVO.setSpuDescription(SpuInfo.getSpuDescription());
        spuInfoVO.setCategoryId(SpuInfo.getCategoryId());
        spuInfoVO.setBrandId(SpuInfo.getBrandId());
        spuInfoVO.setWeight(SpuInfo.getWeight());
        spuInfoVO.setPublishStatus(SpuInfo.getPublishStatus());
        spuInfoVO.setSearchValue(SpuInfo.getSearchValue());
        spuInfoVO.setCreateBy(SpuInfo.getCreateBy());
        spuInfoVO.setCreateTime(SpuInfo.getCreateTime());
        spuInfoVO.setDeleted(SpuInfo.getDeleted());
        spuInfoVO.setUpdateBy(SpuInfo.getUpdateBy());
        spuInfoVO.setUpdateTime(SpuInfo.getUpdateTime());
        spuInfoVO.setRemark(SpuInfo.getRemark());
        spuInfoVO.setParams(SpuInfo.getParams());

        spuInfoVO.setCategoryName(categoryName);
        spuInfoVO.setBrandName(brandName);
        return spuInfoVO;
    }

    public static AttributeVO attribute2AttributeVO(Attribute Attribute, String categoryAttributeName) {
        if (Attribute == null) {
            return null;
        }
        AttributeVO attributeVO = new AttributeVO();
        attributeVO.setId(Attribute.getId());
        attributeVO.setProductAttributeCategoryId(Attribute.getProductAttributeCategoryId());
        attributeVO.setProductAttributeCategoryName(categoryAttributeName);
        attributeVO.setName(Attribute.getName());
        attributeVO.setSelectType(Attribute.getSelectType());
        attributeVO.setInputType(Attribute.getInputType());
        attributeVO.setInputList(Attribute.getInputList());
        attributeVO.setSort(Attribute.getSort());
        attributeVO.setFilterType(Attribute.getFilterType());
        attributeVO.setIcon(Attribute.getIcon());
        attributeVO.setSearchType(Attribute.getSearchType());
        attributeVO.setRelatedStatus(Attribute.getRelatedStatus());
        attributeVO.setHandAddStatus(Attribute.getHandAddStatus());
        attributeVO.setAttrType(Attribute.getAttrType());
        attributeVO.setSearchValue(Attribute.getSearchValue());
        attributeVO.setCreateBy(Attribute.getCreateBy());
        attributeVO.setCreateTime(Attribute.getCreateTime());
        attributeVO.setDeleted(Attribute.getDeleted());
        attributeVO.setUpdateBy(Attribute.getUpdateBy());
        attributeVO.setUpdateTime(Attribute.getUpdateTime());
        attributeVO.setRemark(Attribute.getRemark());
        attributeVO.setParams(Attribute.getParams());
        return attributeVO;

    }

    public static AttributeCategoryVO attribateCategory2AttributeCategoryVO(AttributeCategory attributeCategory, String categoryName) {
        if (attributeCategory == null) {
            return null;
        }
        AttributeCategoryVO attributeCategoryVO = new AttributeCategoryVO();
        attributeCategoryVO.setId(attributeCategory.getId());
        attributeCategoryVO.setCategoryId(attributeCategory.getCategoryId());
        attributeCategoryVO.setCategoryName(categoryName);
        attributeCategoryVO.setName(attributeCategory.getName());
        attributeCategoryVO.setAttributeCount(attributeCategory.getAttributeCount());
        attributeCategoryVO.setParamCount(attributeCategory.getParamCount());
        attributeCategoryVO.setSearchValue(attributeCategory.getSearchValue());
        attributeCategoryVO.setCreateBy(attributeCategory.getCreateBy());
        attributeCategoryVO.setCreateTime(attributeCategory.getCreateTime());
        attributeCategoryVO.setDeleted(attributeCategory.getDeleted());
        attributeCategoryVO.setUpdateBy(attributeCategory.getUpdateBy());
        attributeCategoryVO.setUpdateTime(attributeCategory.getUpdateTime());
        attributeCategoryVO.setRemark(attributeCategory.getRemark());
        attributeCategoryVO.setParams(attributeCategory.getParams());
        return attributeCategoryVO;
    }

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

    public static CategoryCascadeVO category2CategoryCascadeVO(Category category) {
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
