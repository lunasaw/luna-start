package com.luna.utils;

import com.google.common.base.Joiner;
import com.luna.common.utils.StringUtils;
import com.luna.product.domain.Attribute;
import com.luna.product.domain.req.AttributeReq;

import java.util.Optional;

/**
 * @author luna
 * 2022/5/24
 */
public class Req2DOUtils {

    public static Attribute attributeReq2Attribute(AttributeReq attributeReq) {
        if (attributeReq == null) {
            return null;
        }
        Attribute attribute = new Attribute();
        attribute.setId(attributeReq.getId());
        attribute.setProductAttributeCategoryId(attributeReq.getProductAttributeCategoryId());
        attribute.setName(attributeReq.getName());
        attribute.setSelectType(attributeReq.getSelectType());
        attribute.setInputType(attributeReq.getInputType());
        attribute.setInputList(attributeReq.getInputList());
        String inputList = Optional.ofNullable(attributeReq.getInputListStr()).map(e -> Joiner.on(",").join(e)).orElse(StringUtils.EMPTY);
        if (StringUtils.isNotEmpty(inputList)){
            attribute.setInputList(inputList);
        }
        attribute.setSort(attributeReq.getSort());
        attribute.setFilterType(attributeReq.getFilterType());
        attribute.setIcon(attributeReq.getIcon());
        attribute.setSearchType(attributeReq.getSearchType());
        attribute.setRelatedStatus(attributeReq.getRelatedStatus());
        attribute.setHandAddStatus(attributeReq.getHandAddStatus());
        attribute.setAttrType(attributeReq.getAttrType());
        attribute.setSearchValue(attributeReq.getSearchValue());
        attribute.setCreateBy(attributeReq.getCreateBy());
        attribute.setCreateTime(attributeReq.getCreateTime());
        attribute.setDeleted(attributeReq.getDeleted());
        attribute.setUpdateBy(attributeReq.getUpdateBy());
        attribute.setUpdateTime(attributeReq.getUpdateTime());
        attribute.setRemark(attributeReq.getRemark());
        attribute.setParams(attributeReq.getParams());
        return attribute;
    }
}
