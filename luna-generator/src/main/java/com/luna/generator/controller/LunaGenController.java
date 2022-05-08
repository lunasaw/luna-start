package com.luna.generator.controller;

import com.luna.common.annotation.Log;
import com.luna.common.core.domain.AjaxResult;
import com.luna.common.core.text.Convert;
import com.luna.common.enums.BusinessType;
import com.luna.generator.domain.VmTypeEnum;
import com.luna.generator.domain.VmTypeVO;
import com.luna.generator.service.IVmGenTableService;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 代码生成 操作处理
 *
 * @author luna
 */
@RestController
@RequestMapping("/tool/gen")
public class LunaGenController {

    @Autowired
    private IVmGenTableService iVmGenTableService;

    /**
     * 模版列表
     */
    @PreAuthorize("@ss.hasPermi('tool:gen:preview')")
    @GetMapping("/vmTypes")
    public AjaxResult vmIds() {
        List<VmTypeVO> vmTypeVOList = Arrays.stream(VmTypeEnum.values()).map(e -> new VmTypeVO(e.getType(), e.getDesc())).collect(Collectors.toList());
        return AjaxResult.success(vmTypeVOList);
    }

    /**
     * 预览代码
     */
    @PreAuthorize("@ss.hasPermi('tool:gen:preview')")
    @GetMapping("/preview/{tableId}/{vmId}")
    public AjaxResult previewWithVm(@PathVariable("tableId") Long tableId, @PathVariable("vmId") Integer vmId) {
        Map<String, String> dataMap = iVmGenTableService.previewCode(tableId, vmId);
        return AjaxResult.success(dataMap);
    }

    /**
     * 生成代码（下载方式）
     */
    @PreAuthorize("@ss.hasPermi('tool:gen:code')")
    @Log(title = "代码生成", businessType = BusinessType.GENCODE)
    @GetMapping("/download/{tableName}/{vmId}")
    public void download(HttpServletResponse response, @PathVariable("tableName") String tableName, @PathVariable("vmId") Integer vmId)
        throws IOException {
        byte[] data = iVmGenTableService.downloadCode(tableName, vmId);
        genCode(response, data);
    }

    /**
     * 批量生成代码
     */
    @PreAuthorize("@ss.hasPermi('tool:gen:code')")
    @Log(title = "代码生成", businessType = BusinessType.GENCODE)
    @GetMapping("/batchGenCode/{vmId}")
    public void batchGenCode(HttpServletResponse response, String tables, @PathVariable("vmId") Integer vmId) throws IOException {
        String[] tableNames = Convert.toStrArray(tables);
        byte[] data = iVmGenTableService.downloadCode(tableNames, vmId);
        genCode(response, data);
    }

    /**
     * 生成代码（自定义路径）
     */
    @PreAuthorize("@ss.hasPermi('tool:gen:code')")
    @Log(title = "代码生成", businessType = BusinessType.GENCODE)
    @GetMapping("/genCode/{tableName}/{vmId}")
    public AjaxResult genCode(@PathVariable("tableName") String tableName, @PathVariable("vmId") Integer vmId) {
        iVmGenTableService.generatorCode(tableName, vmId);
        return AjaxResult.success();
    }


    /**
     * 生成代码（自动部署）
     */
    @PreAuthorize("@ss.hasPermi('tool:gen:code')")
    @Log(title = "代码生成", businessType = BusinessType.GENCODE)
    @GetMapping("/genCodeAuto/{tableName}/{vmId}")
    public AjaxResult genCodeAuto(@PathVariable("tableName") String tableName, @PathVariable("vmId") Integer vmId) {
        iVmGenTableService.generatorCode(tableName, vmId);
        return AjaxResult.success();
    }

    /**
     * 生成zip文件
     */
    private void genCode(HttpServletResponse response, byte[] data) throws IOException {
        response.reset();
        response.addHeader("Access-Control-Allow-Origin", "*");
        response.addHeader("Access-Control-Expose-Headers", "Content-Disposition");
        response.setHeader("Content-Disposition", "attachment; filename=\"luna.zip\"");
        response.addHeader("Content-Length", "" + data.length);
        response.setContentType("application/octet-stream; charset=UTF-8");
        IOUtils.write(data, response.getOutputStream());
    }
}
