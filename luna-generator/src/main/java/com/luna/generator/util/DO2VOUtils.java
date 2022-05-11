package com.luna.generator.util;

import com.luna.common.utils.ExtendUtils;
import com.luna.generator.domain.GenTable;
import com.luna.generator.domain.GenTableColumn;
import com.luna.generator.domain.vo.GenTableColumnVO;
import com.luna.generator.domain.vo.GenTableVO;
import com.luna.generator.enums.ColumnSettingEnum;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author luna
 * 2022/5/11
 */
public class DO2VOUtils {

    public static GenTableVO genTable2GenTableVO(GenTable genTable) {
        if (genTable == null) {
            return null;
        }
        GenTableVO genTableVO = new GenTableVO();
        genTableVO.setGenVuePath(genTable.getGenVuePath());
        genTableVO.setTableId(genTable.getTableId());
        genTableVO.setTableName(genTable.getTableName());
        genTableVO.setTableComment(genTable.getTableComment());
        genTableVO.setSubTableName(genTable.getSubTableName());
        genTableVO.setSubTableFkName(genTable.getSubTableFkName());
        genTableVO.setClassName(genTable.getClassName());
        genTableVO.setTplCategory(genTable.getTplCategory());
        genTableVO.setPackageName(genTable.getPackageName());
        genTableVO.setModuleName(genTable.getModuleName());
        genTableVO.setBusinessName(genTable.getBusinessName());
        genTableVO.setFunctionName(genTable.getFunctionName());
        genTableVO.setFunctionAuthor(genTable.getFunctionAuthor());
        genTableVO.setGenType(genTable.getGenType());
        genTableVO.setGenPath(genTable.getGenPath());
        genTableVO.setPkColumn(genTable.getPkColumn());
        genTableVO.setSubTable(genTable2GenTableVO(genTable.getSubTable()));

        List<GenTableColumnVO> tableColumnVOS =
            genTable.getColumns().stream().map(DO2VOUtils::genTableColumn2GenTableColumnVO).collect(Collectors.toList());

        genTableVO.setColumns(tableColumnVOS);
        genTableVO.setOptions(genTable.getOptions());
        genTableVO.setTreeCode(genTable.getTreeCode());
        genTableVO.setTreeParentCode(genTable.getTreeParentCode());
        genTableVO.setTreeName(genTable.getTreeName());
        genTableVO.setParentMenuId(genTable.getParentMenuId());
        genTableVO.setParentMenuName(genTable.getParentMenuName());
        genTableVO.setSearchValue(genTable.getSearchValue());
        genTableVO.setCreateBy(genTable.getCreateBy());
        genTableVO.setCreateTime(genTable.getCreateTime());
        genTableVO.setDeleted(genTable.getDeleted());
        genTableVO.setUpdateBy(genTable.getUpdateBy());
        genTableVO.setUpdateTime(genTable.getUpdateTime());
        genTableVO.setRemark(genTable.getRemark());
        genTableVO.setParams(genTable.getParams());
        return genTableVO;
    }

    public static GenTableColumnVO genTableColumn2GenTableColumnVO(GenTableColumn genTableColumn) {
        if (genTableColumn == null) {
            return null;
        }
        GenTableColumnVO genTableColumnVO = new GenTableColumnVO();
        genTableColumnVO.setColumnId(genTableColumn.getColumnId());
        genTableColumnVO.setTableId(genTableColumn.getTableId());
        genTableColumnVO.setColumnName(genTableColumn.getColumnName());
        genTableColumnVO.setColumnComment(genTableColumn.getColumnComment());
        genTableColumnVO.setColumnType(genTableColumn.getColumnType());
        genTableColumnVO.setJavaType(genTableColumn.getJavaType());
        genTableColumnVO.setJavaField(genTableColumn.getJavaField());
        genTableColumnVO.setIsPk(genTableColumn.getIsPk());
        genTableColumnVO.setIsIncrement(genTableColumn.getIsIncrement());
        genTableColumnVO.setIsRequired(genTableColumn.getIsRequired());
        genTableColumnVO.setIsInsert(genTableColumn.getIsInsert());
        genTableColumnVO.setIsEdit(genTableColumn.getIsEdit());
        genTableColumnVO.setIsList(genTableColumn.getIsList());
        genTableColumnVO.setIsQuery(genTableColumn.getIsQuery());
        genTableColumnVO.setQueryType(genTableColumn.getQueryType());
        genTableColumnVO.setHtmlType(genTableColumn.getHtmlType());
        genTableColumnVO.setDictType(genTableColumn.getDictType());
        genTableColumnVO.setSort(genTableColumn.getSort());

        genTableColumnVO.setSearchValue(genTableColumn.getSearchValue());
        genTableColumnVO.setCreateBy(genTableColumn.getCreateBy());
        genTableColumnVO.setCreateTime(genTableColumn.getCreateTime());
        genTableColumnVO.setDeleted(genTableColumn.getDeleted());
        genTableColumnVO.setUpdateBy(genTableColumn.getUpdateBy());
        genTableColumnVO.setUpdateTime(genTableColumn.getUpdateTime());
        genTableColumnVO.setRemark(genTableColumn.getRemark());
        genTableColumnVO.setParams(genTableColumn.getParams());

        genTableColumnVO.setSortAble(ColumnSettingEnum.getSortAble(genTableColumn.getExtend()));
        return genTableColumnVO;
    }
}
