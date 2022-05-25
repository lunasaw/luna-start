package com.luna.product.domain.vo;

import com.baomidou.mybatisplus.annotation.TableName;
import com.luna.common.annotation.Excel;
import com.luna.common.core.domain.BaseEntity;
import lombok.Data;

/**
 * 品牌分类关联视图对象
 *
 * @author luna
 * @date 2022-05-25
 */
@Data
public class CategoryBrandRelationVO extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /** 关联ID */
    private Long              id;

    /** 品牌名称 */
    private Long              brandId;

    /** 分类名称 */
    private Long              categoryId;

    /** 品牌名称 */
    @Excel(name = "品牌名称")
    private String            brandName;

    /** 分类名称 */
    @Excel(name = "分类名称")
    private String            categoryName;

}