package com.luna.product.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.Objects;
import java.util.ArrayList;
import java.util.stream.Collectors;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.luna.common.annotation.Log;
import com.luna.common.core.controller.BaseController;
import com.luna.common.core.domain.AjaxResult;
import com.luna.common.enums.BusinessType;
import com.luna.product.domain.Category;
import com.luna.product.service.CategoryService;
import com.luna.common.utils.poi.ExcelUtil;
import com.luna.common.core.page.TableDataInfo;

/**
 * 产品分类Controller
 *
 * @author luna
 * @date 2022-05-09
 */
@RestController
@RequestMapping("/product/category")
@Api(tags = "产品分类")
public class CategoryController extends BaseController {
    @Autowired
    private CategoryService categoryService;

    /**
     * 查询产品分类列表
     */
    @PreAuthorize("@ss.hasPermi('product:category:list')")
    @GetMapping("/list")
    public TableDataInfo list(Category category) {
        startPage();
        List<Category> list = categoryService.selectCategoryList(category);
        return getDataTable(list);
    }

    /**
     * 分页查询产品分类列表
     */
    @PreAuthorize("@ss.hasPermi('product:category:list')")
    @ApiOperation(value = "查询产品分类列表")
    @GetMapping("/listPage")
    public TableDataInfo listPage(Category category) {
        Page<Category> page = startPageList();
        IPage<Category> list = categoryService.selectList(page, category);
        return getDataTable(list);
    }

    /**
     * 查询全部产品分类列表
     */
    @PreAuthorize("@ss.hasPermi('product:category:list')")
    @ApiOperation(value = "查询全部产品分类列表")
    @GetMapping("/listAll")
    public List<Category> listAll(Category category) {
        List<Category> list = categoryService.selectAllList(category);
        return list;
    }

    /**
     * ids批量查询品牌列表
     */
    @PreAuthorize("@ss.hasPermi('product:category:list')")
    @ApiOperation(value = "ids查询产品分类列表")
    @GetMapping("/listByIds")
    public List<Category> listByIds(List<Long> ids) {
        return categoryService.selectCategoryByIds(ids);
    }

    /**
     * 导出产品分类列表
     */
    @ApiOperation(value = "导出产品分类列表")
    @PreAuthorize("@ss.hasPermi('product:category:export')")
    @Log(title = "产品分类", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Category category) {
        List<Category> list = categoryService.selectCategoryList(category);
        ExcelUtil<Category> util = new ExcelUtil<Category>(Category.class);
        util.exportExcel(response, list, "产品分类数据");
    }

    /**
     * 获取产品分类详细信息
     */
    @ApiOperation(value = "获取产品分类详细信息")
    @PreAuthorize("@ss.hasPermi('product:category:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(categoryService.selectCategoryById(id));
    }

    /**
     * 新增产品分类
     */
    @ApiOperation(value = "新增产品分类")
    @PreAuthorize("@ss.hasPermi('product:category:add')")
    @Log(title = "产品分类", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Category category) {
        return toAjax(categoryService.insertCategory(category));
    }

    /**
     * 批量新增
     */
    @PreAuthorize("@ss.hasPermi('product:category:add')")
    @ApiOperation(value = "批量新增产品分类列表")
    @Log(title = "产品分类", businessType = BusinessType.INSERT)
    @PostMapping("/addList")
    public Boolean addList(List<Category> categoryList) {
        categoryList = categoryList.stream().filter(e -> Objects.nonNull(e.getId())).collect(Collectors.toList());
        return categoryService.saveBatch(categoryList);
    }


    /**
     * 修改产品分类
     */
    @ApiOperation(value = "修改产品分类")
    @PreAuthorize("@ss.hasPermi('product:category:edit')")
    @Log(title = "产品分类", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Category category) {
        return toAjax(categoryService.updateCategory(category));
    }

    /**
     * 批量修改产品分类
     */
    @ApiOperation(value = "修改产品分类")
    @PreAuthorize("@ss.hasPermi('product:category:edit')")
    @Log(title = "产品分类", businessType = BusinessType.UPDATE)
    @PutMapping("/editList")
    public AjaxResult editList(@RequestBody List<Category> categoryList) {
        categoryList = categoryList.stream().filter(Objects::nonNull).collect(Collectors.toList());
        return toAjax(categoryService.updateBatchById(categoryList));
    }

    /**
     * 删除产品分类
     */
    @ApiOperation(value = "删除产品分类")
    @PreAuthorize("@ss.hasPermi('product:category:remove')")
    @Log(title = "产品分类", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult removeByIds(@PathVariable Long[] ids) {
        return toAjax(categoryService.deleteCategoryByIds(ids));
    }

    /**
     * 单个删除产品分类
     */
    @ApiOperation(value = "删除产品分类")
    @PreAuthorize("@ss.hasPermi('product:category:remove')")
    @Log(title = "产品分类", businessType = BusinessType.DELETE)
    @DeleteMapping("/{id}")
    public AjaxResult remove(@PathVariable Long id) {
        return toAjax(categoryService.deleteCategoryById(id));
    }
}
