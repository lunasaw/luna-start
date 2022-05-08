package com.luna.generator.service;

import java.util.Map;
import java.util.zip.ZipOutputStream;


/**
 * 业务 服务层
 * 
 * @author luna
 */
public interface IVmGenTableService
{

    /**
     * 预览代码
     *
     * @param tableId 表编号
     * @return 预览数据列表
     */
    public Map<String, String> previewCode(Long tableId, Integer vmId);

    /**
     * 生成代码（下载方式）
     *
     * @param tableName 表名称
     * @return 数据
     */
    public byte[] downloadCode(String tableName, Integer vmId);

    /**
     * 生成代码（自定义路径）
     *
     * @param tableName 表名称
     * @return 数据
     */
    public void generatorCode(String tableName, Integer vmId);


    /**
     * 生成代码（自动部署）
     *
     * @param tableName 表名称
     * @return 数据
     */
    public void generatorCodeAuto(String tableName, Integer vmId);

    /**
     * 生成代码（自定义路径）
     *
     * @param tableName 表名称
     * @return 数据
     */
    public void generatorCode(String tableName, Integer vmId,  ZipOutputStream zip);


    /**
     * 批量生成代码（下载方式）
     *
     * @param tableNames 表数组
     * @return 数据
     */
    public byte[] downloadCode(String[] tableNames, Integer vmId);


}
