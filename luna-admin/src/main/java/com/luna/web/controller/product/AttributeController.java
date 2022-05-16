package com.luna.web.controller.product;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.luna.product.domain.vo.AttributeVO;
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
import com.luna.product.domain.Attribute;
import com.luna.product.service.AttributeService;
import com.luna.common.utils.poi.ExcelUtil;
import com.luna.common.core.page.TableDataInfo;

/**
 * 商品属性参数Controller
 *
 * @author luna
 * @date 2022-05-15
 */
@RestController
@RequestMapping("/product/attribute")
@Api(tags = "商品属性参数")
public class AttributeController extends BaseController {
    @Autowired
    private AttributeService attributeService;

    /**
     * 查询商品属性参数列表
     */
    @PreAuthorize("@ss.hasPermi('product:attribute:list')")
    @GetMapping("/list")
    public TableDataInfo list(Attribute attribute) {
        startPage();
        PageInfo pageInfo = attributeService.selectAttributeList(attribute);
        TableDataInfo dataTable = getDataTable(pageInfo.getList());
        dataTable.setTotal(pageInfo.getTotal());
        return dataTable;
    }

    /**
     * 分页查询商品属性参数列表
     */
    @PreAuthorize("@ss.hasPermi('product:attribute:list')")
    @ApiOperation(value = "查询商品属性参数列表")
    @GetMapping("/listPage")
    public TableDataInfo listPage(Attribute attribute) {
        Page<Attribute> page = startPageList();
        IPage<AttributeVO> list = attributeService.selectVOList(page, attribute);
        return getDataTable(list);
    }

    /**
     * 查询全部商品属性参数列表
     */
    @PreAuthorize("@ss.hasPermi('product:attribute:list')")
    @ApiOperation(value = "查询全部商品属性参数列表")
    @GetMapping("/listAll")
    public AjaxResult listAll(Attribute attribute) {
        List<Attribute> list = attributeService.selectAllList(attribute);
        return AjaxResult.success(list);
    }

    /**
     * ids批量查询品牌列表
     */
    @PreAuthorize("@ss.hasPermi('product:attribute:list')")
    @ApiOperation(value = "ids查询商品属性参数列表")
    @GetMapping("/listByIds")
    public List<Attribute> listByIds(List<Long> ids) {
        return attributeService.selectAttributeByIds(ids);
    }

    /**
     * 导出商品属性参数列表
     */
    @ApiOperation(value = "导出商品属性参数列表")
    @PreAuthorize("@ss.hasPermi('product:attribute:export')")
    @Log(title = "商品属性参数", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Attribute attribute) {
        List<Attribute> list = attributeService.selectAllList(attribute);
        ExcelUtil<Attribute> util = new ExcelUtil<Attribute>(Attribute.class);
        util.exportExcel(response, list, "商品属性参数数据");
    }

    /**
     * 获取商品属性参数详细信息
     */
    @ApiOperation(value = "获取商品属性参数详细信息")
    @PreAuthorize("@ss.hasPermi('product:attribute:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(attributeService.selectAttributeById(id));
    }

    /**
     * 新增商品属性参数
     */
    @ApiOperation(value = "新增商品属性参数")
    @PreAuthorize("@ss.hasPermi('product:attribute:add')")
    @Log(title = "商品属性参数", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Attribute attribute) {
        return toAjax(attributeService.insertAttribute(attribute));
    }

    /**
     * 批量新增
     */
    @PreAuthorize("@ss.hasPermi('product:attribute:add')")
    @ApiOperation(value = "批量新增商品属性参数列表")
    @Log(title = "商品属性参数", businessType = BusinessType.INSERT)
    @PostMapping("/addList")
    public Boolean addList(List<Attribute> attributeList) {
        attributeList = attributeList.stream().filter(e -> Objects.nonNull(e.getId())).collect(Collectors.toList());
        return attributeService.saveBatch(attributeList);
    }

    /**
     * 修改商品属性参数
     */
    @ApiOperation(value = "修改商品属性参数")
    @PreAuthorize("@ss.hasPermi('product:attribute:edit')")
    @Log(title = "商品属性参数", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Attribute attribute) {
        return toAjax(attributeService.updateAttribute(attribute));
    }

    /**
     * 批量修改商品属性参数
     */
    @ApiOperation(value = "修改商品属性参数")
    @PreAuthorize("@ss.hasPermi('product:attribute:edit')")
    @Log(title = "商品属性参数", businessType = BusinessType.UPDATE)
    @PutMapping("/editList")
    public AjaxResult editList(@RequestBody List<Attribute> attributeList) {
        attributeList = attributeList.stream().filter(Objects::nonNull).collect(Collectors.toList());
        return toAjax(attributeService.updateBatchById(attributeList));
    }

    /**
     * 删除商品属性参数
     */
    @ApiOperation(value = "删除商品属性参数")
    @PreAuthorize("@ss.hasPermi('product:attribute:remove')")
    @Log(title = "商品属性参数", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult removeByIds(@PathVariable Long[] ids) {
        return toAjax(attributeService.deleteAttributeByIds(ids));
    }

    /**
     * 逻辑删除商品属性参数
     */
    @ApiOperation(value = "逻辑删除商品属性参数")
    @PreAuthorize("@ss.hasPermi('product:attribute:remove')")
    @Log(title = "商品属性参数", businessType = BusinessType.DELETE)
    @DeleteMapping("delete")
    public AjaxResult remove(@PathVariable Attribute attribute) {
        return toAjax(attributeService.deleteById(attribute));
    }
}
