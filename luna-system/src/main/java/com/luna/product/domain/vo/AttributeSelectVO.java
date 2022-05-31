package com.luna.product.domain.vo;

import com.luna.common.annotation.Excel;
import lombok.Data;

import java.util.List;

/**
 * @author luna
 * 2022/5/31
 */
@Data
public class AttributeSelectVO {
    /** 属性所属分类ID */
    @Excel(name = "属性所属分类ID")
    private Long              productAttributeCategoryId;

    /** 属性所属分类ID */
    @Excel(name = "属性所属分类名称")
    private String            productAttributeCategoryName;

    private List<AttributeVO> attributes;
}
