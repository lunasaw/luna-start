package com.luna.product.domain.req;

import com.baomidou.mybatisplus.annotation.TableName;
import com.luna.common.annotation.Excel;
import com.luna.common.core.domain.BaseEntity;

import lombok.Data;

/**
 * 商品属性参数对象 tb_pms_attribute
 *
 * @author luna
 * @date 2022-05-15
 */
@Data
public class AttributeReq extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /** 属性ID */
    private Long              id;

    /** 属性所属分类ID */
    @Excel(name = "属性所属分类ID")
    private Long              productAttributeCategoryId;

    /** 属性名称 */
    @Excel(name = "属性名称")
    private String            name;


    private Long              categoryId;

}