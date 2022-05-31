package com.luna.product.mapper;

import java.util.List;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.luna.product.domain.SkuInfo;

/**
 * sku信息Mapper接口
 * 
 * @author luna
 * @date 2022-05-31
 */
public interface SkuInfoMapper extends BaseMapper<SkuInfo> {
    /**
     * 查询sku信息
     * 
     * @param skuId sku信息主键
     * @return sku信息
     */
    public SkuInfo selectSkuInfoBySkuId(Long skuId);

    /**
     * 查询sku信息列表
     * 
     * @param skuInfo sku信息
     * @return sku信息集合
     */
    public List<SkuInfo> selectSkuInfoList(SkuInfo skuInfo);

    /**
     * 新增sku信息
     * 
     * @param skuInfo sku信息
     * @return 结果
     */
    public int insertSkuInfo(SkuInfo skuInfo);

    /**
     * 修改sku信息
     * 
     * @param skuInfo sku信息
     * @return 结果
     */
    public int updateSkuInfo(SkuInfo skuInfo);

    /**
     * 删除sku信息
     * 
     * @param skuId sku信息主键
     * @return 结果
     */
    public int deleteSkuInfoBySkuId(Long skuId);

    /**
     * 批量删除sku信息
     * 
     * @param skuIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSkuInfoBySkuIds(Long[] skuIds);


}
