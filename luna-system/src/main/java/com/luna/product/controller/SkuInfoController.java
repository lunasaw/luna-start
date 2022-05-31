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
import com.luna.product.domain.vo.SkuInfoVO;
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
import com.luna.product.domain.SkuInfo;
import com.luna.product.service.SkuInfoService;
import com.luna.common.utils.poi.ExcelUtil;
import com.luna.common.core.page.TableDataInfo;

/**
 * sku信息Controller
 *
 * @author luna
 * @date 2022-05-31
 */
@RestController
@RequestMapping("/skuInfo/skuInfo")
@Api(tags = "sku信息")
public class SkuInfoController extends BaseController {
    @Autowired
    private SkuInfoService skuInfoService;

    /**
     * 查询sku信息列表
     */
    @PreAuthorize("@ss.hasPermi('skuInfo:skuInfo:list')")
    @GetMapping("/list")
    public TableDataInfo list(SkuInfo skuInfo) {
        startPage();
        PageInfo pageInfo = skuInfoService.selectSkuInfoList(skuInfo);
        TableDataInfo dataTable = getDataTable(pageInfo.getList());
        dataTable.setTotal(pageInfo.getTotal());
        return dataTable;
    }

    /**
     * 分页查询sku信息列表
     */
    @PreAuthorize("@ss.hasPermi('skuInfo:skuInfo:list')")
    @ApiOperation(value = "查询sku信息列表")
    @GetMapping("/listPage")
    public TableDataInfo listPage(SkuInfo skuInfo) {
        Page<SkuInfo> page = startPageList();
        IPage<SkuInfoVO> list = skuInfoService.selectVOList(page, skuInfo);
        return getDataTable(list);
    }

    /**
     * 查询全部sku信息列表
     */
    @PreAuthorize("@ss.hasPermi('skuInfo:skuInfo:list')")
    @ApiOperation(value = "查询全部sku信息列表")
    @GetMapping("/listAll")
    public List<SkuInfoVO> listAll(SkuInfo skuInfo) {
        List<SkuInfoVO> list = skuInfoService.selectAllList(skuInfo);
        return list;
    }

    /**
     * ids批量查询品牌列表
     */
    @PreAuthorize("@ss.hasPermi('skuInfo:skuInfo:list')")
    @ApiOperation(value = "ids查询sku信息列表")
    @GetMapping("/listByIds")
    public AjaxResult listByIds(List<Long> ids) {
        List<SkuInfo> list = skuInfoService.selectSkuInfoByIds(ids);
        return AjaxResult.success(list);
    }

    /**
     * 导出sku信息列表
     */
    @ApiOperation(value = "导出sku信息列表")
    @PreAuthorize("@ss.hasPermi('skuInfo:skuInfo:export')")
    @Log(title = "sku信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SkuInfo skuInfo) {
        List<SkuInfoVO> list = skuInfoService.selectAllList(skuInfo);
        ExcelUtil<SkuInfoVO> util = new ExcelUtil<SkuInfoVO>(SkuInfoVO.class);
        util.exportExcel(response, list, "sku信息数据");
    }

    /**
     * 获取sku信息详细信息
     */
    @ApiOperation(value = "获取sku信息详细信息")
    @PreAuthorize("@ss.hasPermi('skuInfo:skuInfo:query')")
    @GetMapping(value = "/{skuId}")
    public AjaxResult getInfo(@PathVariable("skuId") Long skuId) {
        return AjaxResult.success(skuInfoService.selectSkuInfoBySkuId(skuId));
    }

    /**
     * 新增sku信息
     */
    @ApiOperation(value = "新增sku信息")
    @PreAuthorize("@ss.hasPermi('skuInfo:skuInfo:add')")
    @Log(title = "sku信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SkuInfo skuInfo) {
        return toAjax(skuInfoService.insertSkuInfo(skuInfo));
    }

    /**
     * 批量新增
     */
    @PreAuthorize("@ss.hasPermi('skuInfo:skuInfo:add')")
    @ApiOperation(value = "批量新增sku信息列表")
    @Log(title = "sku信息", businessType = BusinessType.INSERT)
    @PostMapping("/addList")
    public Boolean addList(List<SkuInfo> skuInfoList) {
        skuInfoList = skuInfoList.stream().filter(e -> Objects.nonNull(e.getSkuId())).collect(Collectors.toList());
        return skuInfoService.saveBatch(skuInfoList);
    }

    /**
     * 修改sku信息
     */
    @ApiOperation(value = "修改sku信息")
    @PreAuthorize("@ss.hasPermi('skuInfo:skuInfo:edit')")
    @Log(title = "sku信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SkuInfo skuInfo) {
        return toAjax(skuInfoService.updateSkuInfo(skuInfo));
    }

    /**
     * 批量修改sku信息
     */
    @ApiOperation(value = "修改sku信息")
    @PreAuthorize("@ss.hasPermi('skuInfo:skuInfo:edit')")
    @Log(title = "sku信息", businessType = BusinessType.UPDATE)
    @PutMapping("/editList")
    public AjaxResult editList(@RequestBody List<SkuInfo> skuInfoList) {
        skuInfoList = skuInfoList.stream().filter(Objects::nonNull).collect(Collectors.toList());
        return toAjax(skuInfoService.updateskuInfoBatch(skuInfoList));
    }

    /**
     * 删除sku信息
     */
    @ApiOperation(value = "删除sku信息")
    @PreAuthorize("@ss.hasPermi('skuInfo:skuInfo:remove')")
    @Log(title = "sku信息", businessType = BusinessType.DELETE)
    @DeleteMapping("/{skuIds}")
    public AjaxResult removeByIds(@PathVariable Long[] skuIds) {
        return toAjax(skuInfoService.deleteSkuInfoBySkuIds(skuIds));
    }

    /**
     * 逻辑删除sku信息
     */
    @ApiOperation(value = "逻辑删除sku信息")
    @PreAuthorize("@ss.hasPermi('skuInfo:skuInfo:remove')")
    @Log(title = "sku信息", businessType = BusinessType.DELETE)
    @DeleteMapping("delete")
    public AjaxResult remove(@RequestBody SkuInfo skuInfo) {
        return toAjax(skuInfoService.delete(skuInfo));
    }

    /**
     * 批量逻辑删除sku信息
     */
    @ApiOperation(value = "逻辑批量删除sku信息")
    @PreAuthorize("@ss.hasPermi('product:category:remove')")
    @Log(title = "sku信息", businessType = BusinessType.DELETE)
    @DeleteMapping("deleteBatch")
    public AjaxResult removeBatch(@RequestBody List<Long> skuInfoIds) {
        return toAjax(skuInfoService.deleteIds(skuInfoIds));
    }
}
