package com.luna.product.mapper;

import java.util.List;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.luna.product.domain.SkuSaleAttrValue;

/**
 * sku销售属性&值Mapper接口
 * 
 * @author luna
 * @date 2022-05-31
 */
public interface SkuSaleAttrValueMapper extends BaseMapper<SkuSaleAttrValue> {
    /**
     * 查询sku销售属性&值
     * 
     * @param id sku销售属性&值主键
     * @return sku销售属性&值
     */
    public SkuSaleAttrValue selectSkuSaleAttrValueById(Long id);

    /**
     * 查询sku销售属性&值列表
     * 
     * @param skuSaleAttrValue sku销售属性&值
     * @return sku销售属性&值集合
     */
    public List<SkuSaleAttrValue> selectSkuSaleAttrValueList(SkuSaleAttrValue skuSaleAttrValue);

    /**
     * 新增sku销售属性&值
     * 
     * @param skuSaleAttrValue sku销售属性&值
     * @return 结果
     */
    public int insertSkuSaleAttrValue(SkuSaleAttrValue skuSaleAttrValue);

    /**
     * 修改sku销售属性&值
     * 
     * @param skuSaleAttrValue sku销售属性&值
     * @return 结果
     */
    public int updateSkuSaleAttrValue(SkuSaleAttrValue skuSaleAttrValue);

    /**
     * 删除sku销售属性&值
     * 
     * @param id sku销售属性&值主键
     * @return 结果
     */
    public int deleteSkuSaleAttrValueById(Long id);

    /**
     * 批量删除sku销售属性&值
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSkuSaleAttrValueByIds(Long[] ids);


}
