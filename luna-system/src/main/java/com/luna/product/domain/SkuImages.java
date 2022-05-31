package com.luna.product.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.luna.common.annotation.Excel;
import com.luna.common.core.domain.BaseEntity;
import lombok.Data;

/**
 * SKU图片对象 tb_pms_sku_images
 *
 * @author luna
 * @date 2022-05-31
 */
@Data
@TableName("tb_pms_sku_images")
public class SkuImages extends BaseEntity{
private static final long serialVersionUID=1L;

/** id */
    @Excel(name = "id")
private Long id;

/** sku_id */
    @Excel(name = "sku_id")
private Long skuId;

/** 图片地址 */
    @Excel(name = "图片地址")
private String imgUrl;

/** 排序 */
    @Excel(name = "排序")
private Long imgSort;

/** 是否默认图 */
    @Excel(name = "是否默认图")
private Long defaultImg;

        }