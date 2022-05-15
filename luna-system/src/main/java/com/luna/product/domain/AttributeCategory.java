package com.luna.product.domain;

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
@TableName("tb_pms_product_attribute_category")
public class AttributeCategory extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /** 分类属性ID */
    private Long              id;

    /** 分类ID */
    @Excel(name = "分类ID")
    private Long              categoryId;

    /** 分类属性名 */
    @Excel(name = "分类属性名")
    private String            name;

    /** 属性数量 */
    @Excel(name = "属性数量")
    private Long              attributeCount;

    /** 参数数量 */
    @Excel(name = "参数数量")
    private Long              paramCount;

}