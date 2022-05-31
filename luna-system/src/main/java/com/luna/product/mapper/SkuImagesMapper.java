package com.luna.product.mapper;

import java.util.List;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.luna.product.domain.SkuImages;

/**
 * SKU图片Mapper接口
 * 
 * @author luna
 * @date 2022-05-31
 */
public interface SkuImagesMapper extends BaseMapper<SkuImages> {
    /**
     * 查询SKU图片
     * 
     * @param id SKU图片主键
     * @return SKU图片
     */
    public SkuImages selectSkuImagesById(Long id);

    /**
     * 查询SKU图片列表
     * 
     * @param skuImages SKU图片
     * @return SKU图片集合
     */
    public List<SkuImages> selectSkuImagesList(SkuImages skuImages);

    /**
     * 新增SKU图片
     * 
     * @param skuImages SKU图片
     * @return 结果
     */
    public int insertSkuImages(SkuImages skuImages);

    /**
     * 修改SKU图片
     * 
     * @param skuImages SKU图片
     * @return 结果
     */
    public int updateSkuImages(SkuImages skuImages);

    /**
     * 删除SKU图片
     * 
     * @param id SKU图片主键
     * @return 结果
     */
    public int deleteSkuImagesById(Long id);

    /**
     * 批量删除SKU图片
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSkuImagesByIds(Long[] ids);


}