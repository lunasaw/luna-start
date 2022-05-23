package com.luna.product.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.luna.common.annotation.Excel;
import com.luna.common.core.domain.BaseEntity;
import lombok.Data;

/**
 * SPU图片对象 tb_pms_spu_images
 *
 * @author luna
 * @date 2022-05-23
 */
@Data
@TableName("tb_pms_spu_images")
public class SpuImages extends BaseEntity{
private static final long serialVersionUID=1L;

/** id */
    @Excel(name = "id")
private Long id;

/** SPU_ID */
    @Excel(name = "SPU_ID")
private Long spuId;

/** 图片名 */
    @Excel(name = "图片名")
private String imgName;

/** 图片地址 */
    @Excel(name = "图片地址")
private String imgUrl;

/** 顺序 */
    @Excel(name = "顺序")
private Long imgSort;

/** 是否默认图 */
    @Excel(name = "是否默认图")
private Integer defaultImg;

        }