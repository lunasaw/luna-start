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
import com.luna.product.domain.vo.SkuSaleAttrValueVO;
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
import com.luna.product.domain.SkuSaleAttrValue;
import com.luna.product.service.SkuSaleAttrValueService;
import com.luna.common.utils.poi.ExcelUtil;
import com.luna.common.core.page.TableDataInfo;

/**
 * sku销售属性&值Controller
 *
 * @author luna
 * @date 2022-05-31
 */
@RestController
@RequestMapping("/product/skuAttrValue")
@Api(tags = "sku销售属性&值" )
public class SkuSaleAttrValueController extends BaseController {
    @Autowired
    private SkuSaleAttrValueService skuSaleAttrValueService;

    /**
     * 查询sku销售属性&值列表
     */
    @PreAuthorize("@ss.hasPermi('product:skuAttrValue:list')")
    @GetMapping("/list")
    public TableDataInfo list(SkuSaleAttrValue skuSaleAttrValue) {
        startPage();
        PageInfo pageInfo = skuSaleAttrValueService.selectSkuSaleAttrValueList(skuSaleAttrValue);
        TableDataInfo dataTable = getDataTable(pageInfo.getList());
        dataTable.setTotal(pageInfo.getTotal());
        return dataTable;
    }

    /**
     * 分页查询sku销售属性&值列表
     */
    @PreAuthorize("@ss.hasPermi('product:skuAttrValue:list')")
    @ApiOperation(value = "查询sku销售属性&值列表" )
    @GetMapping("/listPage" )
    public TableDataInfo listPage(SkuSaleAttrValue skuSaleAttrValue) {
        Page<SkuSaleAttrValue> page = startPageList();
        IPage<SkuSaleAttrValueVO> list = skuSaleAttrValueService.selectVOList(page, skuSaleAttrValue);
        return getDataTable(list);
    }

    /**
     * 查询全部sku销售属性&值列表
     */
    @PreAuthorize("@ss.hasPermi('product:skuAttrValue:list')")
    @ApiOperation(value = "查询全部sku销售属性&值列表" )
    @GetMapping("/listAll" )
    public List<SkuSaleAttrValueVO> listAll(SkuSaleAttrValue skuSaleAttrValue) {
        List<SkuSaleAttrValueVO> list = skuSaleAttrValueService.selectAllList(skuSaleAttrValue);
        return list;
    }

    /**
     * ids批量查询品牌列表
     */
    @PreAuthorize("@ss.hasPermi('product:skuAttrValue:list')")
    @ApiOperation(value = "ids查询sku销售属性&值列表")
    @GetMapping("/listByIds")
    public AjaxResult listByIds(List<Long> ids) {
        List<SkuSaleAttrValue> list = skuSaleAttrValueService.selectSkuSaleAttrValueByIds(ids);
        return AjaxResult.success(list);
    }

    /**
     * 导出sku销售属性&值列表
     */
    @ApiOperation(value = "导出sku销售属性&值列表")
    @PreAuthorize("@ss.hasPermi('product:skuAttrValue:export')")
    @Log(title = "sku销售属性&值", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SkuSaleAttrValue skuSaleAttrValue) {
        List<SkuSaleAttrValueVO> list = skuSaleAttrValueService.selectAllList(skuSaleAttrValue);
        ExcelUtil<SkuSaleAttrValueVO> util = new ExcelUtil<SkuSaleAttrValueVO>(SkuSaleAttrValueVO. class);
        util.exportExcel(response, list, "sku销售属性&值数据");
    }

    /**
     * 获取sku销售属性&值详细信息
     */
    @ApiOperation(value = "获取sku销售属性&值详细信息")
    @PreAuthorize("@ss.hasPermi('product:skuAttrValue:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id" ) Long id) {
        return AjaxResult.success(skuSaleAttrValueService.selectSkuSaleAttrValueById(id));
    }

    /**
     * 新增sku销售属性&值
     */
    @ApiOperation(value = "新增sku销售属性&值" )
    @PreAuthorize("@ss.hasPermi('product:skuAttrValue:add')")
    @Log(title = "sku销售属性&值" , businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SkuSaleAttrValue skuSaleAttrValue) {
        return toAjax(skuSaleAttrValueService.insertSkuSaleAttrValue(skuSaleAttrValue));
    }

    /**
     * 批量新增
     */
    @PreAuthorize("@ss.hasPermi('product:skuAttrValue:add')")
    @ApiOperation(value = "批量新增sku销售属性&值列表" )
    @Log(title = "sku销售属性&值" , businessType = BusinessType.INSERT)
    @PostMapping("/addList" )
    public Boolean addList(List<SkuSaleAttrValue> skuSaleAttrValueList) {
        skuSaleAttrValueList = skuSaleAttrValueList.stream().filter(e-> Objects.nonNull(e.getId())).collect(Collectors.toList());
        return skuSaleAttrValueService.saveBatch(skuSaleAttrValueList);
    }


    /**
     * 修改sku销售属性&值
     */
    @ApiOperation(value = "修改sku销售属性&值" )
    @PreAuthorize("@ss.hasPermi('product:skuAttrValue:edit')")
    @Log(title = "sku销售属性&值" , businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SkuSaleAttrValue skuSaleAttrValue) {
        return toAjax(skuSaleAttrValueService.updateSkuSaleAttrValue(skuSaleAttrValue));
    }

    /**
     * 批量修改sku销售属性&值
     */
    @ApiOperation(value = "修改sku销售属性&值" )
    @PreAuthorize("@ss.hasPermi('product:skuAttrValue:edit')")
    @Log(title = "sku销售属性&值" , businessType = BusinessType.UPDATE)
    @PutMapping("/editList")
    public AjaxResult editList(@RequestBody List<SkuSaleAttrValue> skuSaleAttrValueList) {
        skuSaleAttrValueList = skuSaleAttrValueList.stream().filter(Objects::nonNull).collect(Collectors.toList());
        return toAjax(skuSaleAttrValueService.updateskuSaleAttrValueBatch(skuSaleAttrValueList));
    }

    /**
     * 删除sku销售属性&值
     */
    @ApiOperation(value = "删除sku销售属性&值" )
    @PreAuthorize("@ss.hasPermi('product:skuAttrValue:remove')")
    @Log(title = "sku销售属性&值" , businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}" )
    public AjaxResult removeByIds(@PathVariable Long[] ids) {
        return toAjax(skuSaleAttrValueService.deleteSkuSaleAttrValueByIds(ids));
    }

    /**
     * 逻辑删除sku销售属性&值
     */
    @ApiOperation(value = "逻辑删除sku销售属性&值")
    @PreAuthorize("@ss.hasPermi('product:skuAttrValue:remove')")
    @Log(title = "sku销售属性&值", businessType = BusinessType.DELETE)
    @DeleteMapping("delete")
    public AjaxResult remove(@RequestBody SkuSaleAttrValue skuSaleAttrValue) {
        return toAjax(skuSaleAttrValueService.delete(skuSaleAttrValue));
    }


    /**
     * 批量逻辑删除sku销售属性&值
     */
    @ApiOperation(value = "逻辑批量删除sku销售属性&值")
    @PreAuthorize("@ss.hasPermi('product:category:remove')")
    @Log(title = "sku销售属性&值", businessType = BusinessType.DELETE)
    @DeleteMapping("deleteBatch")
    public AjaxResult removeBatch(@RequestBody List<Long> skuSaleAttrValueIds) {
        return toAjax(skuSaleAttrValueService.deleteIds(skuSaleAttrValueIds));
    }
}
