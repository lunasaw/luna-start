package com.luna.product.domain;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;
import com.luna.common.annotation.Excel;
import com.luna.common.core.domain.BaseEntity;
import lombok.Data;

/**
 * 商品SPU信息对象 tb_pms_spu_info
 *
 * @author luna
 * @date 2022-05-24
 */
@Data
@TableName("tb_pms_spu_info")
public class SpuInfo extends BaseEntity {
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
    private Long              categoryId;

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