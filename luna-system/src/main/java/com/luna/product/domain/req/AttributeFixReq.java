package com.luna.product.domain.req;

import java.util.List;

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
public class AttributeFixReq extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /** 属性所属分类ID */
    @Excel(name = "属性所属分类ID")
    private Long              productAttributeCategoryId;

    @Excel(name = "属性Id列表")
    private List<Long>        attrIds;

}