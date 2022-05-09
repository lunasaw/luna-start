package com.luna.product.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.luna.common.annotation.Excel;
    import com.luna.common.core.domain.BaseEntity;
import lombok.Data;

/**
 * 产品属性分类对象 tb_pms_product_attribute_category
 *
 * @author luna
 * @date 2022-05-09
 */
@Data
@TableName("tb_pms_product_attribute_category")
public class CategoryAttribute extends BaseEntity
        {
private static final long serialVersionUID=1L;

    /** 属性ID */
    private Long id;

    /** 属性名称 */
            @Excel(name = "属性名称")
    private String name;

    /** 属性数量 */
            @Excel(name = "属性数量")
    private Long attributeCount;

    /** 参数数量 */
            @Excel(name = "参数数量")
    private Long paramCount;

        }