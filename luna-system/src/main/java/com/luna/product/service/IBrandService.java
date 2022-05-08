package com.luna.product.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.luna.product.domain.Brand;

import java.util.List;

/**
 * 品牌Service接口
 * 
 * @author luna
 * @date 2022-05-01
 */
public interface IBrandService extends IService<Brand> {
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
     * 查询品牌列表
     *
     * @param brand 品牌
     * @return 品牌集合
     */
    public List<Brand> selectList(Brand brand);

    /**
     * 查询品牌列表
     *
     * @param ids 品牌
     * @return 品牌集合
     */
    public List<Brand> selectBrandByIds(List<Long> ids);

    /**
     * 分页查询品牌列表
     *
     * @param brand 品牌
     * @param page 分页参数
     * @return 品牌集合
     */
    public IPage<Brand> selectList(IPage<Brand> page, Brand brand);

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
     * 批量删除品牌
     * 
     * @param ids 需要删除的品牌主键集合
     * @return 结果
     */
    public int deleteBrandByIds(Long[] ids);

    /**
     * 删除品牌信息
     * 
     * @param id 品牌主键
     * @return 结果
     */
    public int deleteBrandById(Long id);
}
