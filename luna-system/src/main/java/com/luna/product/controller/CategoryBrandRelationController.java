package com.luna.product.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.Objects;
import java.util.ArrayList;
import com.github.pagehelper.PageInfo;
import java.util.stream.Collectors;
import com.luna.product.domain.vo.CategoryBrandRelationVO;
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
import com.luna.product.domain.CategoryBrandRelation;
import com.luna.product.service.CategoryBrandRelationService;
import com.luna.common.utils.poi.ExcelUtil;
import com.luna.common.core.page.TableDataInfo;

/**
 * 品牌分类关联Controller
 *
 * @author luna
 * @date 2022-05-25
 */
@RestController
@RequestMapping("/product/brandRelation")
@Api(tags = "品牌分类关联")
public class CategoryBrandRelationController extends BaseController {
    @Autowired
    private CategoryBrandRelationService categoryBrandRelationService;

    /**
     * 查询品牌分类关联列表
     */
    @PreAuthorize("@ss.hasPermi('product:brandRelation:list')")
    @GetMapping("/list")
    public TableDataInfo list(CategoryBrandRelation categoryBrandRelation) {
        startPage();
        PageInfo pageInfo = categoryBrandRelationService.selectCategoryBrandRelationList(categoryBrandRelation);
        TableDataInfo dataTable = getDataTable(pageInfo.getList());
        dataTable.setTotal(pageInfo.getTotal());
        return dataTable;
    }

    /**
     * 分页查询品牌分类关联列表
     */
    @PreAuthorize("@ss.hasPermi('product:brandRelation:list')")
    @ApiOperation(value = "查询品牌分类关联列表")
    @GetMapping("/listPage")
    public TableDataInfo listPage(CategoryBrandRelation categoryBrandRelation) {
        Page<CategoryBrandRelation> page = startPageList();
        IPage<CategoryBrandRelationVO> list = categoryBrandRelationService.selectVOList(page, categoryBrandRelation);
        return getDataTable(list);
    }

    /**
     * 查询全部品牌分类关联列表
     */
    @PreAuthorize("@ss.hasPermi('product:brandRelation:list')")
    @ApiOperation(value = "查询全部品牌分类关联列表")
    @GetMapping("/listAll")
    public List<CategoryBrandRelationVO> listAll(CategoryBrandRelation categoryBrandRelation) {
        List<CategoryBrandRelationVO> list = categoryBrandRelationService.selectAllList(categoryBrandRelation);
        return list;
    }

    /**
     * ids批量查询品牌列表
     */
    @PreAuthorize("@ss.hasPermi('product:brandRelation:list')")
    @ApiOperation(value = "ids查询品牌分类关联列表")
    @GetMapping("/listByIds")
    public AjaxResult listByIds(List<Long> ids) {
        List<CategoryBrandRelation> list = categoryBrandRelationService.selectCategoryBrandRelationByIds(ids);
        return AjaxResult.success(list);
    }

    /**
     * 导出品牌分类关联列表
     */
    @ApiOperation(value = "导出品牌分类关联列表")
    @PreAuthorize("@ss.hasPermi('product:brandRelation:export')")
    @Log(title = "品牌分类关联", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, CategoryBrandRelation categoryBrandRelation) {
        List<CategoryBrandRelationVO> list = categoryBrandRelationService.selectAllList(categoryBrandRelation);
        ExcelUtil<CategoryBrandRelationVO> util = new ExcelUtil<CategoryBrandRelationVO>(CategoryBrandRelationVO.class);
        util.exportExcel(response, list, "品牌分类关联数据");
    }

    /**
     * 获取品牌分类关联详细信息
     */
    @ApiOperation(value = "获取品牌分类关联详细信息")
    @PreAuthorize("@ss.hasPermi('product:brandRelation:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(categoryBrandRelationService.selectCategoryBrandRelationById(id));
    }

    /**
     * 新增品牌分类关联
     */
    @ApiOperation(value = "新增品牌分类关联")
    @PreAuthorize("@ss.hasPermi('product:brandRelation:add')")
    @Log(title = "品牌分类关联", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody CategoryBrandRelation categoryBrandRelation) {
        return toAjax(categoryBrandRelationService.insertCategoryBrandRelation(categoryBrandRelation));
    }

    /**
     * 批量新增
     */
    @PreAuthorize("@ss.hasPermi('product:brandRelation:add')")
    @ApiOperation(value = "批量新增品牌分类关联列表")
    @Log(title = "品牌分类关联", businessType = BusinessType.INSERT)
    @PostMapping("/addList")
    public Boolean addList(List<CategoryBrandRelation> categoryBrandRelationList) {
        categoryBrandRelationList = categoryBrandRelationList.stream().filter(e -> Objects.nonNull(e.getId())).collect(Collectors.toList());
        return categoryBrandRelationService.saveBatch(categoryBrandRelationList);
    }

    /**
     * 修改品牌分类关联
     */
    @ApiOperation(value = "修改品牌分类关联")
    @PreAuthorize("@ss.hasPermi('product:brandRelation:edit')")
    @Log(title = "品牌分类关联", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody CategoryBrandRelation categoryBrandRelation) {
        return toAjax(categoryBrandRelationService.updateCategoryBrandRelation(categoryBrandRelation));
    }

    /**
     * 批量修改品牌分类关联
     */
    @ApiOperation(value = "修改品牌分类关联")
    @PreAuthorize("@ss.hasPermi('product:brandRelation:edit')")
    @Log(title = "品牌分类关联", businessType = BusinessType.UPDATE)
    @PutMapping("/editList")
    public AjaxResult editList(@RequestBody List<CategoryBrandRelation> categoryBrandRelationList) {
        categoryBrandRelationList = categoryBrandRelationList.stream().filter(Objects::nonNull).collect(Collectors.toList());
        return toAjax(categoryBrandRelationService.updatecategoryBrandRelationBatch(categoryBrandRelationList));
    }

    /**
     * 删除品牌分类关联
     */
    @ApiOperation(value = "删除品牌分类关联")
    @PreAuthorize("@ss.hasPermi('product:brandRelation:remove')")
    @Log(title = "品牌分类关联", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult removeByIds(@PathVariable Long[] ids) {
        return toAjax(categoryBrandRelationService.deleteCategoryBrandRelationByIds(ids));
    }

    /**
     * 逻辑删除品牌分类关联
     */
    @ApiOperation(value = "逻辑删除品牌分类关联")
    @PreAuthorize("@ss.hasPermi('product:brandRelation:remove')")
    @Log(title = "品牌分类关联", businessType = BusinessType.DELETE)
    @DeleteMapping("delete")
    public AjaxResult remove(@RequestBody CategoryBrandRelation categoryBrandRelation) {
        return toAjax(categoryBrandRelationService.delete(categoryBrandRelation));
    }

    /**
     * 批量逻辑删除品牌分类关联
     */
    @ApiOperation(value = "逻辑批量删除品牌分类关联")
    @PreAuthorize("@ss.hasPermi('product:category:remove')")
    @Log(title = "品牌分类关联", businessType = BusinessType.DELETE)
    @DeleteMapping("deleteBatch")
    public AjaxResult removeBatch(@RequestBody List<Long> categoryBrandRelationIds) {
        return toAjax(categoryBrandRelationService.deleteIds(categoryBrandRelationIds));
    }
}
