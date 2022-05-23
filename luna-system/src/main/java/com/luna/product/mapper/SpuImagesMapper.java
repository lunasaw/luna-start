package com.luna.product.mapper;

import java.util.List;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.luna.product.domain.SpuImages;

/**
 * SPU图片Mapper接口
 * 
 * @author luna
 * @date 2022-05-23
 */
public interface SpuImagesMapper extends BaseMapper<SpuImages> {
    /**
     * 查询SPU图片
     * 
     * @param id SPU图片主键
     * @return SPU图片
     */
    public SpuImages selectSpuImagesById(Long id);

    /**
     * 查询SPU图片列表
     * 
     * @param spuImages SPU图片
     * @return SPU图片集合
     */
    public List<SpuImages> selectSpuImagesList(SpuImages spuImages);

    /**
     * 新增SPU图片
     * 
     * @param spuImages SPU图片
     * @return 结果
     */
    public int insertSpuImages(SpuImages spuImages);

    /**
     * 修改SPU图片
     * 
     * @param spuImages SPU图片
     * @return 结果
     */
    public int updateSpuImages(SpuImages spuImages);

    /**
     * 删除SPU图片
     * 
     * @param id SPU图片主键
     * @return 结果
     */
    public int deleteSpuImagesById(Long id);

    /**
     * 批量删除SPU图片
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSpuImagesByIds(Long[] ids);


}
