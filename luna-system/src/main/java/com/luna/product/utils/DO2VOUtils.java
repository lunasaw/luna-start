package com.luna.product.utils;

import com.luna.product.domain.SkuImages;
import com.luna.product.domain.vo.SkuImagesVO;

/**
 * SKU图片Controller
 *
 * @author luna
 * @date 2022-05-31
 */
public class DO2VOUtils {

    public static SkuImagesVO skuImages2SkuImagesVO(SkuImages SkuImages) {
        if (SkuImages == null) {
            return null;
        }
        SkuImagesVO skuImagesVO = new SkuImagesVO();
        return skuImagesVO;
    }

}