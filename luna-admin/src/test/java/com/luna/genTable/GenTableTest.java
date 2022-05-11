package com.luna.genTable;

import com.alibaba.fastjson.JSON;
import com.luna.AppTest;
import com.luna.generator.domain.GenTable;
import com.luna.generator.domain.GenTableColumn;
import com.luna.generator.domain.req.GenTableColumnReq;
import com.luna.generator.domain.req.GenTableReq;
import com.luna.generator.domain.vo.GenTableVO;
import com.luna.generator.mapper.GenTableColumnMapper;
import com.luna.generator.mapper.GenTableMapper;
import com.luna.generator.util.DO2VOUtils;
import com.luna.generator.util.Req2DOUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author luna
 * 2022/5/11
 */
public class GenTableTest extends AppTest {

    @Autowired
    private GenTableMapper       genTableMapper;

    @Autowired
    private GenTableColumnMapper genTableColumnMapper;

    @Test
    public void atest() {
        GenTable genTable = genTableMapper.selectGenTableById(2L);
        // System.out.println(JSON.toJSONString(genTable));
        GenTableVO genTableVO = DO2VOUtils.genTable2GenTableVO(genTable);

        String s = JSON.toJSONString(genTableVO);
        System.out.println(JSON.toJSONString(genTableVO.getVmTypeVO()));

        GenTableReq genTableReq = JSON.parseObject(s, GenTableReq.class);
        // System.out.println(JSON.toJSONString(genTableReq));

        List<GenTableColumnReq> columns = genTableReq.getColumns();
        GenTableColumnReq genTableColumnReq = columns.get(0);
        genTableColumnReq.setSortAble(true);

        GenTable genTable1 = genTableMapper.selectGenTableById(genTableReq.getTableId());

        GenTable genTable2 = Req2DOUtils.genTableReq2GenTable(genTable1, genTableReq);
        // System.out.println(JSON.toJSONString(genTable2.getColumns()));

        List<GenTableColumn> columns1 = genTable2.getColumns();

        GenTableColumn genTableColumn = columns1.get(0);
        GenTableColumn genTableColumn1 = genTableColumnMapper.selectGenTableColumnByColumnId(genTableColumn.getColumnId());
        System.out.println(JSON.toJSONString(genTableColumn1));
    }
}
