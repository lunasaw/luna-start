package com.luna.product.utils;

import com.luna.product.domain.SkuSaleAttrValue;
import com.luna.product.domain.vo.SkuSaleAttrValueVO;

/**
 * sku销售属性&值Controller
 *
 * @author luna
 * @date 2022-05-31
 */
public class DO2VOUtils {

    public static SkuSaleAttrValueVO skuSaleAttrValue2SkuSaleAttrValueVO (SkuSaleAttrValue SkuSaleAttrValue) {
        if (SkuSaleAttrValue ==null){
            return null;
        }
            SkuSaleAttrValueVO skuSaleAttrValueVO = new SkuSaleAttrValueVO();
        return skuSaleAttrValueVO;
    }

}