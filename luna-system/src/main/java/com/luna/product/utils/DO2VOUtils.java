package com.luna.product.utils;

import com.luna.product.domain.Attribute;
import com.luna.product.domain.SpuInfo;
import com.luna.product.domain.vo.AttributeVO;
import com.luna.product.domain.vo.SpuInfoVO;

/**
 * 商品SPU信息Controller
 *
 * @author luna
 * @date 2022-05-23
 */
public class DO2VOUtils {

    public static SpuInfoVO spuInfo2SpuInfoVO(SpuInfo SpuInfo) {
        if (SpuInfo == null) {
            return null;
        }

        SpuInfoVO spuInfoVO = new SpuInfoVO();
        spuInfoVO.setId(SpuInfo.getId());
        spuInfoVO.setSpuName(SpuInfo.getSpuName());
        spuInfoVO.setSpuDescription(SpuInfo.getSpuDescription());
        spuInfoVO.setCatalogId(SpuInfo.getCatalogId());
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
}