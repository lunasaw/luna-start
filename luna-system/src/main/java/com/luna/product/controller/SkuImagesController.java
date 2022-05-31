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
import com.luna.product.domain.vo.SkuImagesVO;
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
import com.luna.product.domain.SkuImages;
import com.luna.product.service.SkuImagesService;
import com.luna.common.utils.poi.ExcelUtil;
import com.luna.common.core.page.TableDataInfo;

/**
 * SKU图片Controller
 *
 * @author luna
 * @date 2022-05-31
 */
@RestController
@RequestMapping("/product/skuImages")
@Api(tags = "SKU图片")
public class SkuImagesController extends BaseController {
    @Autowired
    private SkuImagesService skuImagesService;

    /**
     * 查询SKU图片列表
     */
    @PreAuthorize("@ss.hasPermi('product:skuImages:list')")
    @GetMapping("/list")
    public TableDataInfo list(SkuImages skuImages) {
        startPage();
        PageInfo pageInfo = skuImagesService.selectSkuImagesList(skuImages);
        TableDataInfo dataTable = getDataTable(pageInfo.getList());
        dataTable.setTotal(pageInfo.getTotal());
        return dataTable;
    }

    /**
     * 分页查询SKU图片列表
     */
    @PreAuthorize("@ss.hasPermi('product:skuImages:list')")
    @ApiOperation(value = "查询SKU图片列表")
    @GetMapping("/listPage" )
    public TableDataInfo listPage(SkuImages skuImages) {
        Page<SkuImages> page = startPageList();
        IPage<SkuImagesVO> list = skuImagesService.selectVOList(page, skuImages);
        return getDataTable(list);
    }

    /**
     * 查询全部SKU图片列表
     */
    @PreAuthorize("@ss.hasPermi('product:skuImages:list')")
    @ApiOperation(value = "查询全部SKU图片列表")
    @GetMapping("/listAll" )
    public List<SkuImagesVO> listAll(SkuImages skuImages) {
        List<SkuImagesVO> list = skuImagesService.selectAllList(skuImages);
        return list;
    }

    /**
     * ids批量查询品牌列表
     */
    @PreAuthorize("@ss.hasPermi('product:skuImages:list')")
    @ApiOperation(value = "ids查询SKU图片列表")
    @GetMapping("/listByIds")
    public AjaxResult listByIds(List<Long> ids) {
        List<SkuImages> list = skuImagesService.selectSkuImagesByIds(ids);
        return AjaxResult.success(list);
    }

    /**
     * 导出SKU图片列表
     */
    @ApiOperation(value = "导出SKU图片列表")
    @PreAuthorize("@ss.hasPermi('product:skuImages:export')")
    @Log(title = "SKU图片", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SkuImages skuImages) {
        List<SkuImagesVO> list = skuImagesService.selectAllList(skuImages);
        ExcelUtil<SkuImagesVO> util = new ExcelUtil<SkuImagesVO>(SkuImagesVO. class);
        util.exportExcel(response, list, "SKU图片数据");
    }

    /**
     * 获取SKU图片详细信息
     */
    @ApiOperation(value = "获取SKU图片详细信息")
    @PreAuthorize("@ss.hasPermi('product:skuImages:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id" ) Long id) {
        return AjaxResult.success(skuImagesService.selectSkuImagesById(id));
    }

    /**
     * 新增SKU图片
     */
    @ApiOperation(value = "新增SKU图片")
    @PreAuthorize("@ss.hasPermi('product:skuImages:add')")
    @Log(title = "SKU图片", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SkuImages skuImages) {
        return toAjax(skuImagesService.insertSkuImages(skuImages));
    }

    /**
     * 批量新增
     */
    @PreAuthorize("@ss.hasPermi('product:skuImages:add')")
    @ApiOperation(value = "批量新增SKU图片列表")
    @Log(title = "SKU图片", businessType = BusinessType.INSERT)
    @PostMapping("/addList" )
    public Boolean addList(List<SkuImages> skuImagesList) {
        skuImagesList = skuImagesList.stream().filter(e-> Objects.nonNull(e.getId())).collect(Collectors.toList());
        return skuImagesService.saveBatch(skuImagesList);
    }


    /**
     * 修改SKU图片
     */
    @ApiOperation(value = "修改SKU图片")
    @PreAuthorize("@ss.hasPermi('product:skuImages:edit')")
    @Log(title = "SKU图片", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SkuImages skuImages) {
        return toAjax(skuImagesService.updateSkuImages(skuImages));
    }

    /**
     * 批量修改SKU图片
     */
    @ApiOperation(value = "修改SKU图片")
    @PreAuthorize("@ss.hasPermi('product:skuImages:edit')")
    @Log(title = "SKU图片", businessType = BusinessType.UPDATE)
    @PutMapping("/editList")
    public AjaxResult editList(@RequestBody List<SkuImages> skuImagesList) {
        skuImagesList = skuImagesList.stream().filter(Objects::nonNull).collect(Collectors.toList());
        return toAjax(skuImagesService.updateskuImagesBatch(skuImagesList));
    }

    /**
     * 删除SKU图片
     */
    @ApiOperation(value = "删除SKU图片")
    @PreAuthorize("@ss.hasPermi('product:skuImages:remove')")
    @Log(title = "SKU图片", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}" )
    public AjaxResult removeByIds(@PathVariable Long[] ids) {
        return toAjax(skuImagesService.deleteSkuImagesByIds(ids));
    }

    /**
     * 逻辑删除SKU图片
     */
    @ApiOperation(value = "逻辑删除SKU图片")
    @PreAuthorize("@ss.hasPermi('product:skuImages:remove')")
    @Log(title = "SKU图片", businessType = BusinessType.DELETE)
    @DeleteMapping("delete")
    public AjaxResult remove(@RequestBody SkuImages skuImages) {
        return toAjax(skuImagesService.delete(skuImages));
    }


    /**
     * 批量逻辑删除SKU图片
     */
    @ApiOperation(value = "逻辑批量删除SKU图片")
    @PreAuthorize("@ss.hasPermi('product:category:remove')")
    @Log(title = "SKU图片", businessType = BusinessType.DELETE)
    @DeleteMapping("deleteBatch")
    public AjaxResult removeBatch(@RequestBody List<Long> skuImagesIds) {
        return toAjax(skuImagesService.deleteIds(skuImagesIds));
    }
}
