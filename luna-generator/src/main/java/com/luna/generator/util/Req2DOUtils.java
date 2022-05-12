package com.luna.generator.util;

import com.luna.generator.domain.GenTable;
import com.luna.generator.domain.GenTableColumn;
import com.luna.generator.domain.req.GenTableColumnReq;
import com.luna.generator.domain.req.GenTableReq;
import com.luna.generator.enums.ColumnSettingEnum;
import com.luna.generator.enums.TableSettingEnum;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author luna
 * 2022/5/11
 */
public class Req2DOUtils {

    public static GenTableColumn genTableColumnReq2GenTableColumn(GenTableColumn genTableColumn, GenTableColumnReq genTableColumnReq) {
        if (genTableColumn == null) {
            return new GenTableColumn();
        }
        genTableColumn.setColumnId(genTableColumnReq.getColumnId());
        genTableColumn.setTableId(genTableColumnReq.getTableId());
        genTableColumn.setColumnName(genTableColumnReq.getColumnName());
        genTableColumn.setColumnComment(genTableColumnReq.getColumnComment());
        genTableColumn.setColumnType(genTableColumnReq.getColumnType());
        genTableColumn.setJavaType(genTableColumnReq.getJavaType());
        genTableColumn.setJavaField(genTableColumnReq.getJavaField());
        genTableColumn.setIsPk(genTableColumnReq.getIsPk());
        genTableColumn.setIsIncrement(genTableColumnReq.getIsIncrement());
        genTableColumn.setIsRequired(genTableColumnReq.getIsRequired());
        genTableColumn.setIsInsert(genTableColumnReq.getIsInsert());
        genTableColumn.setIsEdit(genTableColumnReq.getIsEdit());
        genTableColumn.setIsList(genTableColumnReq.getIsList());
        genTableColumn.setIsQuery(genTableColumnReq.getIsQuery());
        genTableColumn.setQueryType(genTableColumnReq.getQueryType());
        genTableColumn.setHtmlType(genTableColumnReq.getHtmlType());
        genTableColumn.setDictType(genTableColumnReq.getDictType());
        genTableColumn.setSort(genTableColumnReq.getSort());
        genTableColumn.setSearchValue(genTableColumnReq.getSearchValue());
        genTableColumn.setCreateBy(genTableColumnReq.getCreateBy());
        genTableColumn.setCreateTime(genTableColumnReq.getCreateTime());
        genTableColumn.setDeleted(genTableColumnReq.getDeleted());
        genTableColumn.setUpdateBy(genTableColumnReq.getUpdateBy());
        genTableColumn.setUpdateTime(genTableColumnReq.getUpdateTime());
        genTableColumn.setRemark(genTableColumnReq.getRemark());
        genTableColumn.setParams(genTableColumnReq.getParams());

        genTableColumn.setExtend(ColumnSettingEnum.setSortAble(genTableColumn.getExtend(), genTableColumnReq.getSortAble()));
        return genTableColumn;
    }

    public static GenTable genTableReq2GenTable(GenTable genTable, GenTableReq genTableReq) {
        if (genTable == null) {
            return null;
        }
        genTable.setGenVuePath(genTableReq.getGenVuePath());
        genTable.setTableId(genTableReq.getTableId());
        genTable.setTableName(genTableReq.getTableName());
        genTable.setTableComment(genTableReq.getTableComment());
        genTable.setSubTableName(genTableReq.getSubTableName());
        genTable.setSubTableFkName(genTableReq.getSubTableFkName());
        genTable.setClassName(genTableReq.getClassName());
        genTable.setTplCategory(genTableReq.getTplCategory());
        genTable.setPackageName(genTableReq.getPackageName());
        genTable.setModuleName(genTableReq.getModuleName());
        genTable.setBusinessName(genTableReq.getBusinessName());
        genTable.setFunctionName(genTableReq.getFunctionName());
        genTable.setFunctionAuthor(genTableReq.getFunctionAuthor());
        genTable.setGenType(genTableReq.getGenType());
        genTable.setGenPath(genTableReq.getGenPath());
        genTable.setPkColumn(genTableReq.getPkColumn());
        genTable.setSubTable(genTableReq2GenTable(genTable.getSubTable(), genTableReq.getSubTable()));

        List<GenTableColumn> genTableColumnsReq = genTableReq.getColumns().stream().map(columnReq -> {
            GenTableColumn genTableColumn =
                genTable.getColumns().stream().filter(column -> column.getColumnId().equals(columnReq.getColumnId())).findFirst().orElse(null);
            return genTableColumnReq2GenTableColumn(genTableColumn, columnReq);
        }).collect(Collectors.toList());

        genTable.setColumns(genTableColumnsReq);
        genTable.setOptions(genTableReq.getOptions());
        genTable.setTreeCode(genTableReq.getTreeCode());
        genTable.setTreeParentCode(genTableReq.getTreeParentCode());
        genTable.setTreeName(genTableReq.getTreeName());
        genTable.setParentMenuId(genTableReq.getParentMenuId());
        genTable.setParentMenuName(genTableReq.getParentMenuName());
        genTable.setSearchValue(genTableReq.getSearchValue());
        genTable.setCreateBy(genTableReq.getCreateBy());
        genTable.setCreateTime(genTableReq.getCreateTime());
        genTable.setDeleted(genTableReq.getDeleted());
        genTable.setUpdateBy(genTableReq.getUpdateBy());
        genTable.setUpdateTime(genTableReq.getUpdateTime());
        genTable.setRemark(genTableReq.getRemark());
        genTable.setParams(genTableReq.getParams());

        genTable.setExtend(TableSettingEnum.setTemplate(genTable.getExtend(), genTableReq.getVmType()));
        return genTable;
    }
}
