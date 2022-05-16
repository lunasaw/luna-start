package com.luna.product.utils;

import com.luna.product.domain.Attribute;
import com.luna.product.domain.vo.AttributeVO;

/**
 * 商品属性参数Controller
 *
 * @author luna
 * @date 2022-05-15
 */
public class DO2VOUtils {

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