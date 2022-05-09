package com.luna.product.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.luna.common.annotation.Excel;
import com.luna.common.core.domain.BaseEntity;
import lombok.Data;

/**
 * 产品分类对象 tb_pms_category
 *
 * @author luna
 * @date 2022-05-07
 */
@Data
@TableName("tb_pms_category")
public class Category extends BaseEntity
{
    private static final long serialVersionUID=1L;

    /** 分类ID */
    private Long id;

    /** 上级分类的编号：0表示一级分类 */
    @Excel(name = "上级分类的编号：0表示一级分类")
    private Long parentId;

    /** 分类名称 */
    @Excel(name = "分类名称")
    private String name;

    /** 分类级别：1:一级；2:二级 */
    @Excel(name = "分类级别：1:一级；2:二级")
    private Integer level;

    /** 产品数量 */
    @Excel(name = "产品数量")
    private Long productCount;

    /** 分类属性单位 */
    @Excel(name = "分类属性单位")
    private String productUnit;

    /** 是否显示在导航栏 */
    @Excel(name = "是否显示在导航栏")
    private Integer navStatus;

    /** 显示状态 */
    @Excel(name = "显示状态")
    private Integer showStatus;

    /** 排序 */
    @Excel(name = "排序")
    private Long sort;

    /** 图标 */
    @Excel(name = "图标")
    private String icon;

    /** 关键词 */
    @Excel(name = "关键词")
    private String keywords;

    /** 描述 */
    @Excel(name = "描述")
    private String description;

}