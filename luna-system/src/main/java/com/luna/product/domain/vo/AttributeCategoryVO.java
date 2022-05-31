package com.luna.product.domain.vo;

import com.baomidou.mybatisplus.annotation.TableName;
import com.luna.common.annotation.Excel;
import com.luna.common.core.domain.BaseEntity;

import lombok.Data;

/**
 * 产品属性分类对象 tb_pms_product_attribute_category
 *
 * @author luna
 * @date 2022-05-15
 */
@Data
public class AttributeCategoryVO extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /** 分类属性ID */
    private Long              id;

    /** 分类ID */
    @Excel(name = "分类ID")
    private Long              categoryId;

    /** 所属分类名 */
    @Excel(name = "所属分类名")
    private String            categoryName;

    /** 分类属性名 */
    @Excel(name = "分类属性名")
    private String            name;

    /** 属性数量 */
    @Excel(name = "属性数量")
    private Long              attributeCount;

    /** 参数数量 */
    @Excel(name = "参数数量")
    private Long              paramCount;

    /** 属性类型 */
    @Excel(name = "属性类型")
    private Integer           attrType;

}