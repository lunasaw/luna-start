package com.luna.web.controller.product;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.luna.common.annotation.Log;
import com.luna.common.core.controller.BaseController;
import com.luna.common.core.domain.AjaxResult;
import com.luna.common.core.page.TableDataInfo;
import com.luna.common.enums.BusinessType;
import com.luna.common.utils.poi.ExcelUtil;
import com.luna.product.domain.Brand;
import com.luna.product.service.IBrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 品牌Controller
 * 
 * @author luna
 * @date 2022-05-01
 */
@RestController
@RequestMapping("/product/brand")
public class BrandController extends BaseController {
    @Autowired
    private IBrandService brandService;

    /**
     * 分页查询品牌列表
     */
    @PreAuthorize("@ss.hasPermi('product:brand:list')")
    @GetMapping("/list")
    public TableDataInfo list(Brand brand) {
        startPage();
        List<Brand> brands = brandService.selectList(brand);
        return getDataTable(brands);
    }

    /**
     * 分页查询品牌列表
     */
    @PreAuthorize("@ss.hasPermi('product:brand:list')")
    @GetMapping("/listPage")
    public TableDataInfo pageList(Brand brand) {
        Page<Brand> page = startPageList();
        IPage<Brand> list = brandService.selectList(page, brand);
        return getDataTable(list);
    }

    /**
     * 分页查询全部品牌列表
     */
    @PreAuthorize("@ss.hasPermi('product:brand:list')")
    @GetMapping("/listAll")
    public List<Brand> listAll(Brand brand) {
        return brandService.selectBrandList(brand);
    }

    /**
     * ids批量查询品牌列表
     */
    @PreAuthorize("@ss.hasPermi('product:brand:list')")
    @GetMapping("/listByIds")
    public List<Brand> listByIds(@RequestBody List<Long> ids) {
        return brandService.selectBrandByIds(ids);
    }

    /**
     * 导出品牌列表
     */
    @PreAuthorize("@ss.hasPermi('product:brand:export')")
    @Log(title = "品牌", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Brand brand) {
        List<Brand> list = brandService.selectBrandList(brand);
        ExcelUtil<Brand> util = new ExcelUtil<Brand>(Brand.class);
        util.exportExcel(response, list, "品牌数据");
    }

    /**
     * 获取品牌详细信息
     */
    @PreAuthorize("@ss.hasPermi('product:brand:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getById(@PathVariable("id") Long id) {
        return AjaxResult.success(brandService.selectBrandById(id));
    }

    /**
     * 新增品牌
     */
    @PreAuthorize("@ss.hasPermi('product:brand:add')")
    @Log(title = "品牌", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Brand brand) {
        return toAjax(brandService.insertBrand(brand));
    }

    /**
     * 批量新增品牌
     */
    @PreAuthorize("@ss.hasPermi('product:brand:add')")
    @Log(title = "品牌", businessType = BusinessType.INSERT)
    @PostMapping("/saveBatch")
    public AjaxResult saveBatch(@RequestBody List<Brand> brandList) {
        brandList = brandList.stream().filter(Objects::nonNull).collect(Collectors.toList());
        return toAjax(brandService.saveBatch(brandList));
    }

    /**
     * 修改品牌
     */
    @PreAuthorize("@ss.hasPermi('product:brand:edit')")
    @Log(title = "品牌", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@Valid @RequestBody Brand brand) {
        return toAjax(brandService.updateBrand(brand));
    }

    /**
     * 批量修改品牌
     */
    @PreAuthorize("@ss.hasPermi('product:brand:edit')")
    @Log(title = "品牌", businessType = BusinessType.UPDATE)
    @PutMapping("/editList")
    public AjaxResult editList(@RequestBody List<Brand> brandList) {
        brandList = brandList.stream().filter(e -> Objects.nonNull(e.getId())).collect(Collectors.toList());
        return toAjax(brandService.updateBatchById(brandList));
    }

    /**
     * 删除品牌
     */
    @PreAuthorize("@ss.hasPermi('product:brand:remove')")
    @Log(title = "品牌", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(brandService.deleteBrandByIds(ids));
    }
}
