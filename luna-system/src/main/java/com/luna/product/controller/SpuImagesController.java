package com.luna.product.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.luna.product.domain.vo.SpuImagesVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.util.Objects;
import java.util.ArrayList;

import com.github.pagehelper.PageInfo;

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
import com.luna.product.domain.SpuImages;
import com.luna.product.service.SpuImagesService;
import com.luna.common.utils.poi.ExcelUtil;
import com.luna.common.core.page.TableDataInfo;

/**
 * SPU图片Controller
 *
 * @author luna
 * @date 2022-05-23
 */
@RestController
@RequestMapping("/product/spuImages" )
@Api(tags = "SPU图片" )
public class SpuImagesController extends BaseController {
    @Autowired
    private SpuImagesService spuImagesService;

    /**
     * 查询SPU图片列表
     */
    @PreAuthorize("@ss.hasPermi('product:spuImages:list')")
    @GetMapping("/list")
    public TableDataInfo list(SpuImages spuImages) {
        startPage();
        PageInfo pageInfo = spuImagesService.selectSpuImagesList(spuImages);
        TableDataInfo dataTable = getDataTable(pageInfo.getList());
        dataTable.setTotal(pageInfo.getTotal());
        return dataTable;
    }

    /**
     * 分页查询SPU图片列表
     */
    @PreAuthorize("@ss.hasPermi('product:spuImages:list')" )
    @ApiOperation(value = "查询SPU图片列表" )
    @GetMapping("/listPage" )
    public TableDataInfo listPage(SpuImages spuImages) {
        Page<SpuImages> page = startPageList();
        IPage<SpuImagesVO> list = spuImagesService.selectVOList(page, spuImages);
        return getDataTable(list);
    }

    /**
     * 查询全部SPU图片列表
     */
    @PreAuthorize("@ss.hasPermi('product:spuImages:list')" )
    @ApiOperation(value = "查询全部SPU图片列表" )
    @GetMapping("/listAll" )
    public List<SpuImages> listAll(SpuImages spuImages) {
        List<SpuImages> list = spuImagesService.selectAllList(spuImages);
        return list;
    }

    /**
     * ids批量查询品牌列表
     */
    @PreAuthorize("@ss.hasPermi('product:spuImages:list')")
    @ApiOperation(value = "ids查询SPU图片列表")
    @GetMapping("/listByIds")
    public AjaxResult listByIds(List<Long> ids) {
        List<SpuImages> list = spuImagesService.selectSpuImagesByIds(ids);
        return AjaxResult.success(list);
    }

    /**
     * 导出SPU图片列表
     */
    @ApiOperation(value = "导出SPU图片列表")
    @PreAuthorize("@ss.hasPermi('product:spuImages:export')")
    @Log(title = "SPU图片", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SpuImages spuImages) {
        List<SpuImages> list = spuImagesService.selectAllList(spuImages);
        ExcelUtil<SpuImages> util = new ExcelUtil<SpuImages>(SpuImages. class);
        util.exportExcel(response, list, "SPU图片数据");
    }

    /**
     * 获取SPU图片详细信息
     */
    @ApiOperation(value = "获取SPU图片详细信息")
    @PreAuthorize("@ss.hasPermi('product:spuImages:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id" ) Long id) {
        return AjaxResult.success(spuImagesService.selectSpuImagesById(id));
    }

    /**
     * 新增SPU图片
     */
    @ApiOperation(value = "新增SPU图片" )
    @PreAuthorize("@ss.hasPermi('product:spuImages:add')" )
    @Log(title = "SPU图片" , businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SpuImages spuImages) {
        return toAjax(spuImagesService.insertSpuImages(spuImages));
    }

    /**
     * 批量新增
     */
    @PreAuthorize("@ss.hasPermi('product:spuImages:add')" )
    @ApiOperation(value = "批量新增SPU图片列表" )
    @Log(title = "SPU图片" , businessType = BusinessType.INSERT)
    @PostMapping("/addList" )
    public Boolean addList(List<SpuImages> spuImagesList) {
        spuImagesList = spuImagesList.stream().filter(e-> Objects.nonNull(e.getId())).collect(Collectors.toList());
        return spuImagesService.saveBatch(spuImagesList);
    }


    /**
     * 修改SPU图片
     */
    @ApiOperation(value = "修改SPU图片" )
    @PreAuthorize("@ss.hasPermi('product:spuImages:edit')" )
    @Log(title = "SPU图片" , businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SpuImages spuImages) {
        return toAjax(spuImagesService.updateSpuImages(spuImages));
    }

    /**
     * 批量修改SPU图片
     */
    @ApiOperation(value = "修改SPU图片" )
    @PreAuthorize("@ss.hasPermi('product:spuImages:edit')" )
    @Log(title = "SPU图片" , businessType = BusinessType.UPDATE)
    @PutMapping("/editList")
    public AjaxResult editList(@RequestBody List<SpuImages> spuImagesList) {
        spuImagesList = spuImagesList.stream().filter(Objects::nonNull).collect(Collectors.toList());
        return toAjax(spuImagesService.updateBatchById(spuImagesList));
    }

    /**
     * 删除SPU图片
     */
    @ApiOperation(value = "删除SPU图片" )
    @PreAuthorize("@ss.hasPermi('product:spuImages:remove')" )
    @Log(title = "SPU图片" , businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}" )
    public AjaxResult removeByIds(@PathVariable Long[] ids) {
        return toAjax(spuImagesService.deleteSpuImagesByIds(ids));
    }

    /**
     * 逻辑删除SPU图片
     */
    @ApiOperation(value = "逻辑删除SPU图片")
    @PreAuthorize("@ss.hasPermi('product:spuImages:remove')")
    @Log(title = "SPU图片", businessType = BusinessType.DELETE)
    @DeleteMapping("delete")
    public AjaxResult remove(@RequestBody SpuImages spuImages) {
        return toAjax(spuImagesService.delete(spuImages));
    }


    /**
     * 批量逻辑删除SPU图片
     */
    @ApiOperation(value = "逻辑批量删除SPU图片")
    @PreAuthorize("@ss.hasPermi('product:category:remove')")
    @Log(title = "SPU图片", businessType = BusinessType.DELETE)
    @DeleteMapping("deleteBatch")
    public AjaxResult removeBatch(@RequestBody List<Long> spuImagesIds) {
        return toAjax(spuImagesService.deleteIds(spuImagesIds));
    }
}
