package com.luna.product.domain.vo;

import javax.validation.constraints.Size;

import com.luna.common.annotation.Excel;
import com.luna.common.core.domain.BaseEntity;

import lombok.Data;

/**
 * 品牌对象 tb_pms_brand
 *
 * @author luna
 * @date 2022-05-02
 */
@Data
public class BrandVO extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /** 品牌ID */
    private Long              id;

    /** 品牌名 */
    @Excel(name = "品牌名")
    private String            name;

    /** 首字母 */
    @Excel(name = "首字母")
    private String            firstLetter;

    /** 排序 */
    @Excel(name = "排序")
    private Long              sort;

    /** 是否为品牌制造商 */
    @Excel(name = "是否为品牌制造商")
    private Integer           factoryStatus;

    /** 是否展示 */
    @Excel(name = "是否展示")
    private String            showStatus;

    /** 产品数量 */
    @Excel(name = "产品数量")
    private Long              productCount;

    /** 产品评论数量 */
    @Excel(name = "产品评论数量")
    private Long              productCommentCount;

    /** 品牌logo */
    @Excel(name = "品牌logo")
    private String            logo;

    /** 专区大图 */
    @Excel(name = "专区大图")
    @Size(max = 255, message = "图片上传数量过多")
    private String            bigPic;

    /** 品牌故事 */
    @Excel(name = "品牌故事")
    private String            brandStory;

}