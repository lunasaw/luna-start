package com.luna.utils;

import com.luna.product.domain.Attribute;
import com.luna.product.domain.req.AttributeReq;

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
