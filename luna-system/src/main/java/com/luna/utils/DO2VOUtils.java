package com.luna.utils;

import com.luna.product.domain.*;
import com.luna.product.domain.vo.*;
import org.assertj.core.util.Lists;

import java.util.List;

/**
 * @author luna
 * 2022/5/12
 */
public class DO2VOUtils {

    public static CategoryBrandRelationVO categoryBrandRelation2CategoryBrandRelationVO(CategoryBrandRelation CategoryBrandRelation, String brandName,
        String categoryName) {
        if (CategoryBrandRelation == null) {
            return null;
        }
        CategoryBrandRelationVO categoryBrandRelationVO = new CategoryBrandRelationVO();
        categoryBrandRelationVO.setId(CategoryBrandRelation.getId());
        categoryBrandRelationVO.setBrandId(CategoryBrandRelation.getBrandId());
        categoryBrandRelationVO.setCategoryId(CategoryBrandRelation.getCategoryId());
        categoryBrandRelationVO.setBrandName(brandName);
        categoryBrandRelationVO.setCategoryName(categoryName);
        categoryBrandRelationVO.setSearchValue(CategoryBrandRelation.getSearchValue());
        categoryBrandRelationVO.setCreateBy(CategoryBrandRelation.getCreateBy());
        categoryBrandRelationVO.setCreateTime(CategoryBrandRelation.getCreateTime());
        categoryBrandRelationVO.setDeleted(CategoryBrandRelation.getDeleted());
        categoryBrandRelationVO.setUpdateBy(CategoryBrandRelation.getUpdateBy());
        categoryBrandRelationVO.setUpdateTime(CategoryBrandRelation.getUpdateTime());
        categoryBrandRelationVO.setRemark(CategoryBrandRelation.getRemark());
        categoryBrandRelationVO.setParams(CategoryBrandRelation.getParams());
        return categoryBrandRelationVO;
    }

    public static SpuImagesVO spuImages2SpuImagesVO(SpuImages item, String spuName) {
        if (item == null) {
            return null;
        }
        SpuImagesVO spuImagesVO = new SpuImagesVO();
        spuImagesVO.setId(item.getId());
        spuImagesVO.setSpuId(item.getSpuId());
        spuImagesVO.setImgName(item.getImgName());
        spuImagesVO.setImgUrl(item.getImgUrl());
        spuImagesVO.setImgSort(item.getImgSort());
        spuImagesVO.setDefaultImg(item.getDefaultImg());
        spuImagesVO.setSearchValue(item.getSearchValue());
        spuImagesVO.setCreateBy(item.getCreateBy());
        spuImagesVO.setCreateTime(item.getCreateTime());
        spuImagesVO.setDeleted(item.getDeleted());
        spuImagesVO.setUpdateBy(item.getUpdateBy());
        spuImagesVO.setUpdateTime(item.getUpdateTime());
        spuImagesVO.setRemark(item.getRemark());
        spuImagesVO.setParams(item.getParams());

        spuImagesVO.setSpuName(spuName);
        return spuImagesVO;
    }

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

    public static AttributeVO attribute2AttributeVO(Attribute Attribute, String categoryAttributeName, List<Long> categoryList) {
        return attribute2AttributeVO(Attribute, categoryAttributeName, null, categoryList);
    }

    public static AttributeVO attribute2AttributeVO(Attribute Attribute, String categoryAttributeName, Long categoryId) {
        return attribute2AttributeVO(Attribute, categoryAttributeName, categoryId, Lists.emptyList());
    }

    public static AttributeVO attribute2AttributeVO(Attribute Attribute, String categoryAttributeName, Long categoryId, List<Long> categoryList) {
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

        attributeVO.setCategoryId(categoryId);
        attributeVO.setCategoryIds(categoryList);
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
