package com.luna.product.domain.vo;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;
import com.luna.common.annotation.Excel;
import com.luna.common.core.domain.BaseEntity;
import lombok.Data;

/**
 * sku信息视图对象
 *
 * @author luna
 * @date 2022-05-31
 */
@Data
public class SkuInfoVO extends BaseEntity{
private static final long serialVersionUID = 1L;

/** skuId */
@Excel(name = "skuId")
private Long skuId;

/** spuId */
@Excel(name = "spuId")
private Long spuId;

/** sku名称 */
@Excel(name = "sku名称")
private String skuName;

/** sku介绍描述 */
@Excel(name = "sku介绍描述")
private String skuDesc;

/** 所属分类id */
@Excel(name = "所属分类id")
private Long catalogId;

/** 品牌id */
@Excel(name = "品牌id")
private Long brandId;

/** 默认图片 */
@Excel(name = "默认图片")
private String skuDefaultImg;

/** 标题 */
@Excel(name = "标题")
private String skuTitle;

/** 副标题 */
@Excel(name = "副标题")
private String skuSubtitle;

/** 价格 */
@Excel(name = "价格")
private BigDecimal price;

/** 销量 */
@Excel(name = "销量")
private Long saleCount;

}