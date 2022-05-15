package com.luna.web.controller.product;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.pagehelper.PageInfo;
import com.luna.common.utils.PageUtils;
import com.luna.product.domain.vo.AttributeCategoryVO;
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
import com.luna.product.domain.AttributeCategory;
import com.luna.product.service.AttributeCategoryService;
import com.luna.common.utils.poi.ExcelUtil;
import com.luna.common.core.page.TableDataInfo;

/**
 * 产品属性分类Controller
 *
 * @author luna
 * @date 2022-05-15
 */
@RestController
@RequestMapping("/product/attributeCategory")
@Api(tags = "产品属性分类" )
public class AttributeCategoryController extends BaseController {
    @Autowired
    private AttributeCategoryService attributeCategoryService;

    /**
     * 查询产品属性分类列表
     */
    @PreAuthorize("@ss.hasPermi('product:attributeCategory:list')")
    @GetMapping("/list")
    public TableDataInfo list(AttributeCategory attributeCategory)
    {
        PageUtils.startPage();
        PageInfo pageInfo = attributeCategoryService.selectAttributeCategoryList(attributeCategory);
        TableDataInfo dataTable = getDataTable(pageInfo.getList());
        dataTable.setTotal(pageInfo.getTotal());
        return dataTable;
    }

    /**
     * 分页查询产品属性分类列表
     */
    @PreAuthorize("@ss.hasPermi('product:attributeCategory:list')")
    @ApiOperation(value = "查询产品属性分类列表" )
    @GetMapping("/listPage" )
    public TableDataInfo listPage(AttributeCategory attributeCategory) {
        Page<AttributeCategory> page = startPageList();
        IPage<AttributeCategoryVO> list = attributeCategoryService.selectVOList(page, attributeCategory);
        return getDataTable(list);
    }

    /**
     * 查询全部产品属性分类列表
     */
    @PreAuthorize("@ss.hasPermi('product:attributeCategory:list')")
    @ApiOperation(value = "查询全部产品属性分类列表" )
    @GetMapping("/listAll" )
    public List<AttributeCategory> listAll(AttributeCategory attributeCategory) {
        List<AttributeCategory> list = attributeCategoryService.selectAllList(attributeCategory);
        return list;
    }

    /**
     * ids批量查询品牌列表
     */
    @PreAuthorize("@ss.hasPermi('product:attributeCategory:list')")
    @ApiOperation(value = "ids查询产品属性分类列表" )
    @GetMapping("/listByIds" )
    public List<AttributeCategory> listByIds(List<Long> ids) {
        return attributeCategoryService.selectAttributeCategoryByIds(ids);
    }

    /**
     * 导出产品属性分类列表
     */
    @ApiOperation(value = "导出产品属性分类列表" )
    @PreAuthorize("@ss.hasPermi('product:attributeCategory:export')")
    @Log(title = "产品属性分类" , businessType = BusinessType.EXPORT)
    @PostMapping("/export" )
    public void export(HttpServletResponse response, AttributeCategory attributeCategory) {
        List<AttributeCategory> list = attributeCategoryService.selectAllList(attributeCategory);
        ExcelUtil<AttributeCategory> util = new ExcelUtil<AttributeCategory>(AttributeCategory.class);
        util.exportExcel(response, list, "产品属性分类数据" );
    }

    /**
     * 获取产品属性分类详细信息
     */
    @ApiOperation(value = "获取产品属性分类详细信息" )
    @PreAuthorize("@ss.hasPermi('product:attributeCategory:query')")
    @GetMapping(value = "/{id}" )
    public AjaxResult getInfo(@PathVariable("id" ) Long id) {
        return AjaxResult.success(attributeCategoryService.selectAttributeCategoryById(id));
    }

    /**
     * 新增产品属性分类
     */
    @ApiOperation(value = "新增产品属性分类" )
    @PreAuthorize("@ss.hasPermi('product:attributeCategory:add')")
    @Log(title = "产品属性分类" , businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody AttributeCategory attributeCategory) {
        return toAjax(attributeCategoryService.insertAttributeCategory(attributeCategory));
    }

    /**
     * 批量新增
     */
    @PreAuthorize("@ss.hasPermi('product:attributeCategory:add')")
    @ApiOperation(value = "批量新增产品属性分类列表" )
    @Log(title = "产品属性分类" , businessType = BusinessType.INSERT)
    @PostMapping("/addList" )
    public Boolean addList(List<AttributeCategory> attributeCategoryList) {
        attributeCategoryList = attributeCategoryList.stream().filter(e -> Objects.nonNull(e.getId())).collect(Collectors.toList());
        return attributeCategoryService.saveBatch(attributeCategoryList);
    }


    /**
     * 修改产品属性分类
     */
    @ApiOperation(value = "修改产品属性分类" )
    @PreAuthorize("@ss.hasPermi('product:attributeCategory:edit')")
    @Log(title = "产品属性分类" , businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody AttributeCategory attributeCategory) {
        return toAjax(attributeCategoryService.updateAttributeCategory(attributeCategory));
    }

    /**
     * 批量修改产品属性分类
     */
    @ApiOperation(value = "修改产品属性分类" )
    @PreAuthorize("@ss.hasPermi('product:attributeCategory:edit')")
    @Log(title = "产品属性分类" , businessType = BusinessType.UPDATE)
    @PutMapping("/editList")
    public AjaxResult editList(@RequestBody List<AttributeCategory> attributeCategoryList) {
        attributeCategoryList = attributeCategoryList.stream().filter(Objects::nonNull).collect(Collectors.toList());
        return toAjax(attributeCategoryService.updateBatchById(attributeCategoryList));
    }

    /**
     * 删除产品属性分类
     */
    @ApiOperation(value = "删除产品属性分类" )
    @PreAuthorize("@ss.hasPermi('product:attributeCategory:remove')")
    @Log(title = "产品属性分类" , businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}" )
    public AjaxResult removeByIds(@PathVariable Long[] ids) {
        return toAjax(attributeCategoryService.deleteAttributeCategoryByIds(ids));
    }

    /**
     * 逻辑删除产品属性分类
     */
    @ApiOperation(value = "逻辑删除产品属性分类")
    @PreAuthorize("@ss.hasPermi('product:attributeCategory:remove')")
    @Log(title = "产品属性分类" , businessType = BusinessType.DELETE)
    @DeleteMapping("delete")
    public AjaxResult remove(@PathVariable AttributeCategory attributeCategory) {
        return toAjax(attributeCategoryService.deleteById(attributeCategory));
    }
}
