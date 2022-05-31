package com.luna.product.service;

import java.util.List;
import com.luna.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import com.github.pagehelper.PageInfo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import org.apache.commons.compress.utils.Lists;
import org.apache.commons.collections4.CollectionUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.luna.product.mapper.SkuSaleAttrValueMapper;
import com.luna.product.domain.SkuSaleAttrValue;
import com.luna.product.domain.vo.SkuSaleAttrValueVO;
import com.luna.utils.DO2VOUtils;

/**
 * sku销售属性&值Service业务层处理
 *
 * @author luna
 * @date 2022-05-31
 */
@Service
public class SkuSaleAttrValueService extends ServiceImpl<SkuSaleAttrValueMapper, SkuSaleAttrValue> {
    @Autowired
    private SkuSaleAttrValueMapper skuSaleAttrValueMapper;

    /**
     * 查询sku销售属性&值
     *
     * @param id sku销售属性&值主键
     * @return sku销售属性&值
     */
    public SkuSaleAttrValue selectSkuSaleAttrValueById(Long id) {
        return skuSaleAttrValueMapper.selectSkuSaleAttrValueById(id);
    }

    /**
     * 查询sku销售属性&值列表
     *
     * @param skuSaleAttrValue sku销售属性&值
     * @return sku销售属性&值
     */
    public PageInfo selectSkuSaleAttrValueList(SkuSaleAttrValue skuSaleAttrValue) {
        List<SkuSaleAttrValue> list = skuSaleAttrValueMapper.selectSkuSaleAttrValueList(skuSaleAttrValue);
        PageInfo pageInfo = new PageInfo<>(list);
        List<SkuSaleAttrValueVO> resultList = convertList(list);
        pageInfo.setList(resultList);
        return pageInfo;
    }


    /**
     * 查询全部sku销售属性&值列表
     *
     * @param skuSaleAttrValue sku销售属性&值
     * @return sku销售属性&值
     */
    public List<SkuSaleAttrValueVO> selectAllList(SkuSaleAttrValue skuSaleAttrValue) {

        QueryWrapper<SkuSaleAttrValue> queryWrapper = new QueryWrapper<SkuSaleAttrValue>(skuSaleAttrValue);
        ArrayList<SkuSaleAttrValueVO> list = Lists.newArrayList();
        Page<SkuSaleAttrValue> of = Page.of(0, 2000);
        while (true) {
            Page<SkuSaleAttrValue> skuSaleAttrValuePage = skuSaleAttrValueMapper.selectPage(of, queryWrapper);
            List<SkuSaleAttrValueVO> resultList = convertList(skuSaleAttrValuePage.getRecords());
            if (CollectionUtils.isEmpty(resultList)) {
                break;
            }
            of.setCurrent(of.getCurrent() + 1);
            list.addAll(resultList);
        }

        return list;
    }

    /**
    * ids查询品牌列表
    *
     * @param ids sku销售属性&值
     * @return sku销售属性&值
    */
    public List<SkuSaleAttrValue> selectSkuSaleAttrValueByIds(List<Long> ids){
        if (CollectionUtils.isEmpty(ids)){
            return new ArrayList<>();
        }
        return skuSaleAttrValueMapper.selectBatchIds(ids);
    }

    /**
     * 分页查询sku销售属性&值列表
     *
     * @param skuSaleAttrValue sku销售属性&值
     * @param page 分页参数
     * @return sku销售属性&值
     */
    public IPage<SkuSaleAttrValue> selectList(IPage<SkuSaleAttrValue> page, SkuSaleAttrValue skuSaleAttrValue) {
        QueryWrapper<SkuSaleAttrValue> queryWrapper = new QueryWrapper<SkuSaleAttrValue>(skuSaleAttrValue);
        for (OrderItem order : page.orders()) {
            queryWrapper.orderBy(true, order.isAsc(), order.getColumn());
        }
        Page<SkuSaleAttrValue> selectPage = Page.of(page.getCurrent(), page.getSize());
        selectPage.setMaxLimit(page.maxLimit());
        return skuSaleAttrValueMapper.selectPage(selectPage, queryWrapper);
    }

    /**
     * 分页查询sku销售属性&值VO视图列表
     *
     * @param skuSaleAttrValue sku销售属性&值
     * @param page 分页参数
     * @return sku销售属性&值
     */
    public IPage<SkuSaleAttrValueVO> selectVOList(IPage<SkuSaleAttrValue> page, SkuSaleAttrValue skuSaleAttrValue) {
        IPage<SkuSaleAttrValue> skuSaleAttrValuePage = selectList(page, skuSaleAttrValue);
        List<SkuSaleAttrValue> records = skuSaleAttrValuePage.getRecords();

        List<SkuSaleAttrValueVO> list = convertList(records);

        Page<SkuSaleAttrValueVO> result = new Page<>(skuSaleAttrValuePage.getCurrent(), skuSaleAttrValuePage.getSize(), skuSaleAttrValuePage.getTotal());
        result.setRecords(list);
        return result;
    }

    /**
    * 构造数据
    * @param records
    * @return
    */
    public List<SkuSaleAttrValueVO> convertList(List<SkuSaleAttrValue> records) {
        List<SkuSaleAttrValueVO> list = new ArrayList<>();
        if (CollectionUtils.isEmpty(records)) {
            return list;
        }
        for (SkuSaleAttrValue record:records) {
            SkuSaleAttrValueVO skuSaleAttrValueVO = DO2VOUtils.skuSaleAttrValue2SkuSaleAttrValueVO(record);
            list.add(skuSaleAttrValueVO);
        }
        return list;
    }


    /**
     * 新增sku销售属性&值
     *
     * @param skuSaleAttrValue sku销售属性&值
     * @return 结果
     */
    public int insertSkuSaleAttrValue(SkuSaleAttrValue skuSaleAttrValue) {
                skuSaleAttrValue.setCreateTime(DateUtils.getNowDate());
            return skuSaleAttrValueMapper.insertSkuSaleAttrValue(skuSaleAttrValue);
    }

    /**
     * 修改sku销售属性&值
     *
     * @param skuSaleAttrValue sku销售属性&值
     * @return 结果
     */
    public int updateSkuSaleAttrValue(SkuSaleAttrValue skuSaleAttrValue) {
                skuSaleAttrValue.setUpdateTime(DateUtils.getNowDate());
        return skuSaleAttrValueMapper.updateSkuSaleAttrValue(skuSaleAttrValue);
    }

    /**
     * 批量修改sku销售属性&值列表
     *
     * @param skuSaleAttrValueList sku销售属性&值列表
     * @return 结果
     */
    public Boolean updateskuSaleAttrValueBatch(List<SkuSaleAttrValue> skuSaleAttrValueList) {
        if (CollectionUtils.isEmpty(skuSaleAttrValueList)){
            return true;
        }
        return updateBatchById(skuSaleAttrValueList);
    }

    /**
     * 批量删除sku销售属性&值
     *
     * @param ids 需要删除的sku销售属性&值主键
     * @return 结果
     */
    public int deleteSkuSaleAttrValueByIds(Long[] ids) {
        return skuSaleAttrValueMapper.deleteSkuSaleAttrValueByIds(ids);
    }

    /**
     * 批量逻辑删除sku销售属性&值
     *
     * @param skuSaleAttrValueIds sku销售属性&值
     * @return 结果
     */
    public int deleteIds(List<Long> skuSaleAttrValueIds) {
        if (CollectionUtils.isEmpty(skuSaleAttrValueIds)) {
            return 0;
        }
        QueryWrapper<SkuSaleAttrValue> queryWrapper = new QueryWrapper<SkuSaleAttrValue>();
        queryWrapper.in("id", skuSaleAttrValueIds);
        return skuSaleAttrValueMapper.delete(queryWrapper);
    }


    /**
     * 逻辑删除sku销售属性&值信息
     *
     * @param skuSaleAttrValue skuSaleAttrValue 主键
     * @return 结果
     */
    public int delete(SkuSaleAttrValue skuSaleAttrValue) {
        QueryWrapper<SkuSaleAttrValue> queryWrapper = new QueryWrapper<SkuSaleAttrValue>(skuSaleAttrValue);
        return skuSaleAttrValueMapper.delete(queryWrapper);
    }
}
