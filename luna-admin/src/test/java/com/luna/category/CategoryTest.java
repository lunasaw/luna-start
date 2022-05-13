package com.luna.category;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.luna.AppTest;
import com.luna.common.annotation.DataSource;
import com.luna.common.enums.DataSourceType;
import com.luna.product.domain.Category;
import com.luna.product.mapper.CategoryMapper;
import com.luna.product.service.CategoryService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author luna
 * 2022/5/13
 */
public class CategoryTest extends AppTest {

    @Autowired
    private CategoryMapper  categoryMapper;

    @Autowired
    private CategoryService categoryService;

    @Test
    @DataSource(value = DataSourceType.SLAVE)
    public void atest() {
        Category category = new Category();
        Page page = new Page();

        IPage iPage = categoryService.selectList(page, category);
    }
}
