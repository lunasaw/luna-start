package com.luna.product.domain.vo;

import java.math.BigDecimal;
import com.luna.common.annotation.Excel;
import com.luna.common.core.domain.BaseEntity;
import lombok.Data;

/**
 * 商品SPU信息视图对象
 *
 * @author luna
 * @date 2022-05-23
 */
@Data
public class SpuInfoVO extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /** 商品ID */
    @Excel(name = "商品ID")
    private Long              id;

    /** 商品名称 */
    @Excel(name = "商品名称")
    private String            spuName;

    /** 商品描述 */
    @Excel(name = "商品描述")
    private String            spuDescription;

    /** 所属分类 */
    @Excel(name = "所属分类")
    private Long              catalogId;

    /** 品牌 */
    @Excel(name = "品牌")
    private Long              brandId;

    /** 重量 */
    @Excel(name = "重量")
    private BigDecimal        weight;

    /** 上架状态 */
    @Excel(name = "上架状态")
    private Integer           publishStatus;

}