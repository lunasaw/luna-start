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
import com.luna.product.domain.vo.SpuInfoVO;
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
import com.luna.product.domain.SpuInfo;
import com.luna.product.service.SpuInfoService;
import com.luna.common.utils.poi.ExcelUtil;
import com.luna.common.core.page.TableDataInfo;

/**
 * 商品SPU信息Controller
 *
 * @author luna
 * @date 2022-05-23
 */
@RestController
@RequestMapping("/product/spuInfo")
@Api(tags = "商品SPU信息")
public class SpuInfoController extends BaseController {
    @Autowired
    private SpuInfoService spuInfoService;

    /**
     * 查询商品SPU信息列表
     */
    @PreAuthorize("@ss.hasPermi('product:spuInfo:list')")
    @GetMapping("/list")
    public TableDataInfo list(SpuInfo spuInfo) {
        startPage();
        PageInfo pageInfo = spuInfoService.selectSpuInfoList(spuInfo);
        TableDataInfo dataTable = getDataTable(pageInfo.getList());
        dataTable.setTotal(pageInfo.getTotal());
        return dataTable;
    }

    /**
     * 分页查询商品SPU信息列表
     */
    @PreAuthorize("@ss.hasPermi('product:spuInfo:list')")
    @ApiOperation(value = "查询商品SPU信息列表")
    @GetMapping("/listPage")
    public TableDataInfo listPage(SpuInfo spuInfo) {
        Page<SpuInfo> page = startPageList();
        IPage<SpuInfoVO> list = spuInfoService.selectVOList(page, spuInfo);
        return getDataTable(list);
    }

    /**
     * 查询全部商品SPU信息列表
     */
    @PreAuthorize("@ss.hasPermi('product:spuInfo:list')")
    @ApiOperation(value = "查询全部商品SPU信息列表")
    @GetMapping("/listAll")
    public List<SpuInfo> listAll(SpuInfo spuInfo) {
        List<SpuInfo> list = spuInfoService.selectAllList(spuInfo);
        return list;
    }

    /**
     * ids批量查询品牌列表
     */
    @PreAuthorize("@ss.hasPermi('product:spuInfo:list')")
    @ApiOperation(value = "ids查询商品SPU信息列表")
    @GetMapping("/listByIds")
    public AjaxResult listByIds(List<Long> ids) {
        List<SpuInfo> list = spuInfoService.selectSpuInfoByIds(ids);
        return AjaxResult.success(list);
    }

    /**
     * 导出商品SPU信息列表
     */
    @ApiOperation(value = "导出商品SPU信息列表")
    @PreAuthorize("@ss.hasPermi('product:spuInfo:export')")
    @Log(title = "商品SPU信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SpuInfo spuInfo) {
        List<SpuInfo> list = spuInfoService.selectAllList(spuInfo);
        ExcelUtil<SpuInfo> util = new ExcelUtil<SpuInfo>(SpuInfo.class);
        util.exportExcel(response, list, "商品SPU信息数据");
    }

    /**
     * 获取商品SPU信息详细信息
     */
    @ApiOperation(value = "获取商品SPU信息详细信息")
    @PreAuthorize("@ss.hasPermi('product:spuInfo:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(spuInfoService.selectSpuInfoById(id));
    }

    /**
     * 新增商品SPU信息
     */
    @ApiOperation(value = "新增商品SPU信息")
    @PreAuthorize("@ss.hasPermi('product:spuInfo:add')")
    @Log(title = "商品SPU信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SpuInfo spuInfo) {
        return toAjax(spuInfoService.insertSpuInfo(spuInfo));
    }

    /**
     * 批量新增
     */
    @PreAuthorize("@ss.hasPermi('product:spuInfo:add')")
    @ApiOperation(value = "批量新增商品SPU信息列表")
    @Log(title = "商品SPU信息", businessType = BusinessType.INSERT)
    @PostMapping("/addList")
    public Boolean addList(List<SpuInfo> spuInfoList) {
        spuInfoList = spuInfoList.stream().filter(e -> Objects.nonNull(e.getId())).collect(Collectors.toList());
        return spuInfoService.saveBatch(spuInfoList);
    }


    /**
     * 修改商品SPU信息
     */
    @ApiOperation(value = "修改商品SPU信息")
    @PreAuthorize("@ss.hasPermi('product:spuInfo:edit')")
    @Log(title = "商品SPU信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SpuInfo spuInfo) {
        return toAjax(spuInfoService.updateSpuInfo(spuInfo));
    }

    /**
     * 批量修改商品SPU信息
     */
    @ApiOperation(value = "修改商品SPU信息")
    @PreAuthorize("@ss.hasPermi('product:spuInfo:edit')")
    @Log(title = "商品SPU信息", businessType = BusinessType.UPDATE)
    @PutMapping("/editList")
    public AjaxResult editList(@RequestBody List<SpuInfo> spuInfoList) {
        spuInfoList = spuInfoList.stream().filter(Objects::nonNull).collect(Collectors.toList());
        return toAjax(spuInfoService.updateBatchById(spuInfoList));
    }

    /**
     * 删除商品SPU信息
     */
    @ApiOperation(value = "删除商品SPU信息")
    @PreAuthorize("@ss.hasPermi('product:spuInfo:remove')")
    @Log(title = "商品SPU信息", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult removeByIds(@PathVariable Long[] ids) {
        return toAjax(spuInfoService.deleteSpuInfoByIds(ids));
    }

    /**
     * 逻辑删除商品SPU信息
     */
    @ApiOperation(value = "逻辑删除商品SPU信息")
    @PreAuthorize("@ss.hasPermi('product:spuInfo:remove')")
    @Log(title = "商品SPU信息", businessType = BusinessType.DELETE)
    @DeleteMapping("delete")
    public AjaxResult remove(@RequestBody SpuInfo spuInfo) {
        return toAjax(spuInfoService.delete(spuInfo));
    }


    /**
     * 批量逻辑删除商品SPU信息
     */
    @ApiOperation(value = "逻辑批量删除商品SPU信息")
    @PreAuthorize("@ss.hasPermi('product:category:remove')")
    @Log(title = "商品SPU信息", businessType = BusinessType.DELETE)
    @DeleteMapping("deleteBatch")
    public AjaxResult removeBatch(@RequestBody List<Long> spuInfoIds) {
        return toAjax(spuInfoService.deleteIds(spuInfoIds));
    }
}
