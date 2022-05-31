package com.luna.product.domain.vo;

import com.baomidou.mybatisplus.annotation.TableName;
import com.luna.common.annotation.Excel;
import com.luna.common.core.domain.BaseEntity;
import lombok.Data;

/**
 * sku销售属性&值视图对象
 *
 * @author luna
 * @date 2022-05-31
 */
@Data
public class SkuSaleAttrValueVO extends BaseEntity{
private static final long serialVersionUID = 1L;

/** id */
@Excel(name = "id")
private Long id;

/** sku_id */
@Excel(name = "sku_id")
private Long skuId;

/** attr_id */
@Excel(name = "attr_id")
private Long attrId;

/** 销售属性名 */
@Excel(name = "销售属性名")
private String attrName;

/** 销售属性值 */
@Excel(name = "销售属性值")
private String attrValue;

/** 顺序 */
@Excel(name = "顺序")
private Long attrSort;

}