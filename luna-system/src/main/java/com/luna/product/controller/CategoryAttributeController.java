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
import com.luna.product.domain.CategoryAttribute;
import com.luna.product.service.CategoryAttributeService;
import com.luna.common.utils.poi.ExcelUtil;
import com.luna.common.core.page.TableDataInfo;

/**
 * 产品属性分类Controller
 *
 * @author luna
 * @date 2022-05-09
 */
@RestController
@RequestMapping("/product/categoryAttribute" )
@Api(tags = "产品属性分类" )
public class CategoryAttributeController extends BaseController {
    @Autowired
    private CategoryAttributeService categoryAttributeService;

    /**
     * 查询产品属性分类列表
     */
    @PreAuthorize("@ss.hasPermi('product:categoryAttribute:list')")
    @GetMapping("/list")
    public TableDataInfo list(CategoryAttribute categoryAttribute)
    {
        startPage();
        List<CategoryAttribute> list = categoryAttributeService.selectCategoryAttributeList(categoryAttribute);
        return getDataTable(list);
    }

    /**
     * 分页查询产品属性分类列表
     */
    @PreAuthorize("@ss.hasPermi('product:categoryAttribute:list')" )
    @ApiOperation(value = "查询产品属性分类列表" )
    @GetMapping("/listPage" )
    public TableDataInfo listPage(CategoryAttribute categoryAttribute) {
        Page<CategoryAttribute> page = startPageList();
        IPage<CategoryAttribute> list = categoryAttributeService.selectList(page, categoryAttribute);
        return getDataTable(list);
    }

    /**
     * 查询全部产品属性分类列表
     */
    @PreAuthorize("@ss.hasPermi('product:categoryAttribute:list')" )
    @ApiOperation(value = "查询全部产品属性分类列表" )
    @GetMapping("/listAll" )
    public List<CategoryAttribute> listAll(CategoryAttribute categoryAttribute) {
        List<CategoryAttribute> list = categoryAttributeService.selectAllList(categoryAttribute);
        return list;
    }

    /**
     * ids批量查询品牌列表
     */
    @PreAuthorize("@ss.hasPermi('product:categoryAttribute:list')" )
    @ApiOperation(value = "ids查询产品属性分类列表" )
    @GetMapping("/listByIds" )
    public List<CategoryAttribute> listByIds(List<Long> ids) {
        return categoryAttributeService.selectCategoryAttributeByIds(ids);
    }

    /**
     * 导出产品属性分类列表
     */
    @ApiOperation(value = "导出产品属性分类列表" )
    @PreAuthorize("@ss.hasPermi('product:categoryAttribute:export')" )
    @Log(title = "产品属性分类" , businessType = BusinessType.EXPORT)
    @PostMapping("/export" )
    public void export(HttpServletResponse response, CategoryAttribute categoryAttribute) {
        List<CategoryAttribute> list = categoryAttributeService.selectCategoryAttributeList(categoryAttribute);
        ExcelUtil<CategoryAttribute> util = new ExcelUtil<CategoryAttribute>(CategoryAttribute. class);
        util.exportExcel(response, list, "产品属性分类数据" );
    }

    /**
     * 获取产品属性分类详细信息
     */
    @ApiOperation(value = "获取产品属性分类详细信息" )
    @PreAuthorize("@ss.hasPermi('product:categoryAttribute:query')" )
    @GetMapping(value = "/{id}" )
    public AjaxResult getInfo(@PathVariable("id" ) Long id) {
        return AjaxResult.success(categoryAttributeService.selectCategoryAttributeById(id));
    }

    /**
     * 新增产品属性分类
     */
    @ApiOperation(value = "新增产品属性分类" )
    @PreAuthorize("@ss.hasPermi('product:categoryAttribute:add')" )
    @Log(title = "产品属性分类" , businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody CategoryAttribute categoryAttribute) {
        return toAjax(categoryAttributeService.insertCategoryAttribute(categoryAttribute));
    }

    /**
     * 批量新增
     */
    @PreAuthorize("@ss.hasPermi('product:categoryAttribute:add')" )
    @ApiOperation(value = "批量新增产品属性分类列表" )
    @Log(title = "产品属性分类" , businessType = BusinessType.INSERT)
    @PostMapping("/addList" )
    public Boolean addList(List<CategoryAttribute> categoryAttributeList) {
        categoryAttributeList = categoryAttributeList.stream().filter(e-> Objects.nonNull(e.getId())).collect(Collectors.toList());
        return categoryAttributeService.saveBatch(categoryAttributeList);
    }


    /**
     * 修改产品属性分类
     */
    @ApiOperation(value = "修改产品属性分类" )
    @PreAuthorize("@ss.hasPermi('product:categoryAttribute:edit')" )
    @Log(title = "产品属性分类" , businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody CategoryAttribute categoryAttribute) {
        return toAjax(categoryAttributeService.updateCategoryAttribute(categoryAttribute));
    }

    /**
     * 批量修改产品属性分类
     */
    @ApiOperation(value = "修改产品属性分类" )
    @PreAuthorize("@ss.hasPermi('product:categoryAttribute:edit')" )
    @Log(title = "产品属性分类" , businessType = BusinessType.UPDATE)
    @PutMapping("/editList")
    public AjaxResult editList(@RequestBody List<CategoryAttribute> categoryAttributeList) {
        categoryAttributeList = categoryAttributeList.stream().filter(Objects::nonNull).collect(Collectors.toList());
        return toAjax(categoryAttributeService.updateBatchById(categoryAttributeList));
    }

    /**
     * 删除产品属性分类
     */
    @ApiOperation(value = "删除产品属性分类" )
    @PreAuthorize("@ss.hasPermi('product:categoryAttribute:remove')" )
    @Log(title = "产品属性分类" , businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}" )
    public AjaxResult removeByIds(@PathVariable Long[] ids) {
        return toAjax(categoryAttributeService.deleteCategoryAttributeByIds(ids));
    }

    /**
     * 单个删除产品属性分类
     */
    @ApiOperation(value = "删除产品属性分类" )
    @PreAuthorize("@ss.hasPermi('product:categoryAttribute:remove')" )
    @Log(title = "产品属性分类" , businessType = BusinessType.DELETE)
    @DeleteMapping("/{id}" )
    public AjaxResult remove(@PathVariable Long id) {
        return toAjax(categoryAttributeService.deleteCategoryAttributeById(id));
    }
}
