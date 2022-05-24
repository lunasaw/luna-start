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

    /** 属性选择类型 */
    @Excel(name = "属性选择类型")
    private Integer           selectType;

    /** 属性录入方式 */
    @Excel(name = "属性录入方式")
    private Integer           inputType;

    /** 可选值列表 */
    @Excel(name = "可选值列表")
    private String            inputList;

    /** 排序 */
    @Excel(name = "排序")
    private Long              sort;

    /** 分类筛选样式 */
    @Excel(name = "分类筛选样式")
    private Integer           filterType;

    /** 属性图标 */
    @Excel(name = "属性图标")
    private String            icon;

    /** 检索类型 */
    @Excel(name = "检索类型")
    private Integer           searchType;

    /** 相同属性产品是否关联 */
    @Excel(name = "相同属性产品是否关联")
    private Integer           relatedStatus;

    /** 支持手动新增 */
    @Excel(name = "支持手动新增")
    private Integer           handAddStatus;

    /** 属性的类型 */
    @Excel(name = "属性的类型")
    private Integer           attrType;

    private Long              categoryId;

}