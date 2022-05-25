package com.luna.product.utils;

import com.luna.product.domain.CategoryBrandRelation;
import com.luna.product.domain.vo.CategoryBrandRelationVO;

/**
 * 品牌分类关联Controller
 *
 * @author luna
 * @date 2022-05-25
 */
public class DO2VOUtils {

    public static CategoryBrandRelationVO categoryBrandRelation2CategoryBrandRelationVO(CategoryBrandRelation CategoryBrandRelation) {
        if (CategoryBrandRelation == null) {
            return null;
        }
        CategoryBrandRelationVO categoryBrandRelationVO = new CategoryBrandRelationVO();
        return categoryBrandRelationVO;
    }

}