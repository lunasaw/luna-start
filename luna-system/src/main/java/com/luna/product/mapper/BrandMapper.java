package com.luna.product.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.luna.product.domain.Brand;

import java.util.List;

/**
 * 品牌Mapper接口
 * 
 * @author luna
 * @date 2022-05-01
 */
public interface BrandMapper extends BaseMapper<Brand> {
    /**
     * 查询品牌
     * 
     * @param id 品牌主键
     * @return 品牌
     */
    public Brand selectBrandById(Long id);

    /**
     * 查询品牌列表
     * 
     * @param brand 品牌
     * @return 品牌集合
     */
    public List<Brand> selectBrandList(Brand brand);

    /**
     * 新增品牌
     * 
     * @param brand 品牌
     * @return 结果
     */
    public int insertBrand(Brand brand);

    /**
     * 修改品牌
     * 
     * @param brand 品牌
     * @return 结果
     */
    public int updateBrand(Brand brand);

    /**
     * 删除品牌
     * 
     * @param id 品牌主键
     * @return 结果
     */
    public int deleteBrandById(Long id);

    /**
     * 批量删除品牌
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteBrandByIds(Long[] ids);

}
