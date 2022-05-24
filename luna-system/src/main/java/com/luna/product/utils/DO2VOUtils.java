package com.luna.product.utils;

import com.luna.product.domain.SpuImages;
import com.luna.product.domain.SpuInfo;
import com.luna.product.domain.vo.SpuImagesVO;
import com.luna.product.domain.vo.SpuInfoVO;

/**
 * 商品SPU信息Controller
 *
 * @author luna
 * @date 2022-05-24
 */
public class DO2VOUtils {

    public static SpuImagesVO spuImages2SpuImagesVO (SpuImages SpuImages) {
        if (SpuImages ==null){
            return null;
        }
        SpuImagesVO spuImagesVO = new SpuImagesVO();
        return spuImagesVO;
    }

}