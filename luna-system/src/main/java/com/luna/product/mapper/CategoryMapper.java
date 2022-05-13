package com.luna.product.mapper;

import java.util.List;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.luna.common.annotation.DataSource;
import com.luna.common.enums.DataSourceType;
import com.luna.product.domain.Category;

/**
 * 产品分类Mapper接口
 * 
 * @author luna
 * @date 2022-05-09
 */
public interface CategoryMapper extends BaseMapper<Category> {
    /**
     * 查询产品分类
     * 
     * @param id 产品分类主键
     * @return 产品分类
     */
    public Category selectCategoryById(Long id);

    /**
     * 查询产品分类列表
     * 
     * @param category 产品分类
     * @return 产品分类集合
     */
    public List<Category> selectCategoryList(Category category);

    /**
     * 新增产品分类
     * 
     * @param category 产品分类
     * @return 结果
     */
    public int insertCategory(Category category);

    /**
     * 修改产品分类
     * 
     * @param category 产品分类
     * @return 结果
     */
    public int updateCategory(Category category);

    /**
     * 删除产品分类
     * 
     * @param id 产品分类主键
     * @return 结果
     */
    public int deleteCategoryById(Long id);

    /**
     * 批量删除产品分类
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteCategoryByIds(Long[] ids);


}
