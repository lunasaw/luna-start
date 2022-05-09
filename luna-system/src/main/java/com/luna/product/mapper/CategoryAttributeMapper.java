package com.luna.product.mapper;

import java.util.List;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.luna.product.domain.CategoryAttribute;

/**
 * 产品属性分类Mapper接口
 * 
 * @author luna
 * @date 2022-05-09
 */
public interface CategoryAttributeMapper extends BaseMapper<CategoryAttribute> {
    /**
     * 查询产品属性分类
     * 
     * @param id 产品属性分类主键
     * @return 产品属性分类
     */
    public CategoryAttribute selectCategoryAttributeById(Long id);

    /**
     * 查询产品属性分类列表
     * 
     * @param categoryAttribute 产品属性分类
     * @return 产品属性分类集合
     */
    public List<CategoryAttribute> selectCategoryAttributeList(CategoryAttribute categoryAttribute);

    /**
     * 新增产品属性分类
     * 
     * @param categoryAttribute 产品属性分类
     * @return 结果
     */
    public int insertCategoryAttribute(CategoryAttribute categoryAttribute);

    /**
     * 修改产品属性分类
     * 
     * @param categoryAttribute 产品属性分类
     * @return 结果
     */
    public int updateCategoryAttribute(CategoryAttribute categoryAttribute);

    /**
     * 删除产品属性分类
     * 
     * @param id 产品属性分类主键
     * @return 结果
     */
    public int deleteCategoryAttributeById(Long id);

    /**
     * 批量删除产品属性分类
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteCategoryAttributeByIds(Long[] ids);


}
