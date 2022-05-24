package com.luna.product.mapper;

import java.util.List;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.luna.product.domain.SpuInfo;

/**
 * 商品SPU信息Mapper接口
 * 
 * @author luna
 * @date 2022-05-24
 */
public interface SpuInfoMapper extends BaseMapper<SpuInfo> {
    /**
     * 查询商品SPU信息
     * 
     * @param id 商品SPU信息主键
     * @return 商品SPU信息
     */
    public SpuInfo selectSpuInfoById(Long id);

    /**
     * 查询商品SPU信息列表
     * 
     * @param spuInfo 商品SPU信息
     * @return 商品SPU信息集合
     */
    public List<SpuInfo> selectSpuInfoList(SpuInfo spuInfo);

    /**
     * 新增商品SPU信息
     * 
     * @param spuInfo 商品SPU信息
     * @return 结果
     */
    public int insertSpuInfo(SpuInfo spuInfo);

    /**
     * 修改商品SPU信息
     * 
     * @param spuInfo 商品SPU信息
     * @return 结果
     */
    public int updateSpuInfo(SpuInfo spuInfo);

    /**
     * 删除商品SPU信息
     * 
     * @param id 商品SPU信息主键
     * @return 结果
     */
    public int deleteSpuInfoById(Long id);

    /**
     * 批量删除商品SPU信息
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSpuInfoByIds(Long[] ids);


}
