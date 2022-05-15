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

    public static AttributeVO attribute2AttributeVO(Attribute Attribute) {
        if (Attribute == null) {
            return null;
        }
        AttributeVO attributeVO = new AttributeVO();
        return attributeVO;
    }

}