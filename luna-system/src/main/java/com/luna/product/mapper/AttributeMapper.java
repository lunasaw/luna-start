package com.luna.product.mapper;

import java.util.List;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.luna.product.domain.Attribute;

/**
 * 商品属性参数Mapper接口
 * 
 * @author luna
 * @date 2022-05-15
 */
public interface AttributeMapper extends BaseMapper<Attribute> {
    /**
     * 查询商品属性参数
     * 
     * @param id 商品属性参数主键
     * @return 商品属性参数
     */
    public Attribute selectAttributeById(Long id);

    /**
     * 查询商品属性参数列表
     * 
     * @param attribute 商品属性参数
     * @return 商品属性参数集合
     */
    public List<Attribute> selectAttributeList(Attribute attribute);

    /**
     * 新增商品属性参数
     * 
     * @param attribute 商品属性参数
     * @return 结果
     */
    public int insertAttribute(Attribute attribute);

    /**
     * 修改商品属性参数
     * 
     * @param attribute 商品属性参数
     * @return 结果
     */
    public int updateAttribute(Attribute attribute);

    /**
     * 删除商品属性参数
     * 
     * @param id 商品属性参数主键
     * @return 结果
     */
    public int deleteAttributeById(Long id);

    /**
     * 批量删除商品属性参数
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteAttributeByIds(Long[] ids);

}
