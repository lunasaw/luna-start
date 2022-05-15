package com.luna.product.mapper;

import java.util.List;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.luna.product.domain.AttributeCategory;

/**
 * 产品属性分类Mapper接口
 * 
 * @author luna
 * @date 2022-05-15
 */
public interface AttributeCategoryMapper extends BaseMapper<AttributeCategory> {
    /**
     * 查询产品属性分类
     * 
     * @param id 产品属性分类主键
     * @return 产品属性分类
     */
    public AttributeCategory selectAttributeCategoryById(Long id);

    /**
     * 查询产品属性分类列表
     * 
     * @param attributeCategory 产品属性分类
     * @return 产品属性分类集合
     */
    public List<AttributeCategory> selectAttributeCategoryList(AttributeCategory attributeCategory);

    /**
     * 新增产品属性分类
     * 
     * @param attributeCategory 产品属性分类
     * @return 结果
     */
    public int insertAttributeCategory(AttributeCategory attributeCategory);

    /**
     * 修改产品属性分类
     * 
     * @param attributeCategory 产品属性分类
     * @return 结果
     */
    public int updateAttributeCategory(AttributeCategory attributeCategory);

    /**
     * 删除产品属性分类
     * 
     * @param id 产品属性分类主键
     * @return 结果
     */
    public int deleteAttributeCategoryById(Long id);

    /**
     * 批量删除产品属性分类
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteAttributeCategoryByIds(Long[] ids);


}
