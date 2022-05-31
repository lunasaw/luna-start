package com.luna.product.domain.vo;

import com.baomidou.mybatisplus.annotation.TableName;
import com.luna.common.annotation.Excel;
import com.luna.common.core.domain.BaseEntity;
import lombok.Data;

/**
 * sku图片视图对象
 *
 * @author luna
 * @date 2022-05-31
 */
@Data
public class SkuImagesVO extends BaseEntity{
private static final long serialVersionUID = 1L;

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