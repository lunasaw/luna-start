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
import com.luna.product.mapper.SkuInfoMapper;
import com.luna.product.domain.SkuInfo;
import com.luna.product.domain.vo.SkuInfoVO;
import com.luna.utils.DO2VOUtils;

/**
 * sku信息Service业务层处理
 *
 * @author luna
 * @date 2022-05-31
 */
@Service
public class SkuInfoService extends ServiceImpl<SkuInfoMapper, SkuInfo> {
    @Autowired
    private SkuInfoMapper skuInfoMapper;

    /**
     * 查询sku信息
     *
     * @param skuId sku信息主键
     * @return sku信息
     */
    public SkuInfo selectSkuInfoBySkuId(Long skuId) {
        return skuInfoMapper.selectSkuInfoBySkuId(skuId);
    }

    /**
     * 查询sku信息列表
     *
     * @param skuInfo sku信息
     * @return sku信息
     */
    public PageInfo selectSkuInfoList(SkuInfo skuInfo) {
        List<SkuInfo> list = skuInfoMapper.selectSkuInfoList(skuInfo);
        PageInfo pageInfo = new PageInfo<>(list);
        List<SkuInfoVO> resultList = convertList(list);
        pageInfo.setList(resultList);
        return pageInfo;
    }


    /**
     * 查询全部sku信息列表
     *
     * @param skuInfo sku信息
     * @return sku信息
     */
    public List<SkuInfoVO> selectAllList(SkuInfo skuInfo) {

        QueryWrapper<SkuInfo> queryWrapper = new QueryWrapper<SkuInfo>(skuInfo);
        ArrayList<SkuInfoVO> list = Lists.newArrayList();
        Page<SkuInfo> of = Page.of(0, 2000);
        while (true) {
            Page<SkuInfo> skuInfoPage = skuInfoMapper.selectPage(of, queryWrapper);
            List<SkuInfoVO> resultList = convertList(skuInfoPage.getRecords());
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
     * @param ids sku信息
     * @return sku信息
    */
    public List<SkuInfo> selectSkuInfoByIds(List<Long> ids){
        if (CollectionUtils.isEmpty(ids)){
            return new ArrayList<>();
        }
        return skuInfoMapper.selectBatchIds(ids);
    }

    /**
     * 分页查询sku信息列表
     *
     * @param skuInfo sku信息
     * @param page 分页参数
     * @return sku信息
     */
    public IPage<SkuInfo> selectList(IPage<SkuInfo> page, SkuInfo skuInfo) {
        QueryWrapper<SkuInfo> queryWrapper = new QueryWrapper<SkuInfo>(skuInfo);
        for (OrderItem order : page.orders()) {
            queryWrapper.orderBy(true, order.isAsc(), order.getColumn());
        }
        Page<SkuInfo> selectPage = Page.of(page.getCurrent(), page.getSize());
        selectPage.setMaxLimit(page.maxLimit());
        return skuInfoMapper.selectPage(selectPage, queryWrapper);
    }

    /**
     * 分页查询sku信息VO视图列表
     *
     * @param skuInfo sku信息
     * @param page 分页参数
     * @return sku信息
     */
    public IPage<SkuInfoVO> selectVOList(IPage<SkuInfo> page, SkuInfo skuInfo) {
        IPage<SkuInfo> skuInfoPage = selectList(page, skuInfo);
        List<SkuInfo> records = skuInfoPage.getRecords();

        List<SkuInfoVO> list = convertList(records);

        Page<SkuInfoVO> result = new Page<>(skuInfoPage.getCurrent(), skuInfoPage.getSize(), skuInfoPage.getTotal());
        result.setRecords(list);
        return result;
    }

    /**
    * 构造数据
    * @param records
    * @return
    */
    public List<SkuInfoVO> convertList(List<SkuInfo> records) {
        List<SkuInfoVO> list = new ArrayList<>();
        if (CollectionUtils.isEmpty(records)) {
            return list;
        }
        for (SkuInfo record:records) {
            SkuInfoVO skuInfoVO = DO2VOUtils.skuInfo2SkuInfoVO(record);
            list.add(skuInfoVO);
        }
        return list;
    }


    /**
     * 新增sku信息
     *
     * @param skuInfo sku信息
     * @return 结果
     */
    public int insertSkuInfo(SkuInfo skuInfo) {
                skuInfo.setCreateTime(DateUtils.getNowDate());
            return skuInfoMapper.insertSkuInfo(skuInfo);
    }

    /**
     * 修改sku信息
     *
     * @param skuInfo sku信息
     * @return 结果
     */
    public int updateSkuInfo(SkuInfo skuInfo) {
                skuInfo.setUpdateTime(DateUtils.getNowDate());
        return skuInfoMapper.updateSkuInfo(skuInfo);
    }

    /**
     * 批量修改sku信息列表
     *
     * @param skuInfoList sku信息列表
     * @return 结果
     */
    public Boolean updateskuInfoBatch(List<SkuInfo> skuInfoList) {
        if (CollectionUtils.isEmpty(skuInfoList)){
            return true;
        }
        return updateBatchById(skuInfoList);
    }

    /**
     * 批量删除sku信息
     *
     * @param skuIds 需要删除的sku信息主键
     * @return 结果
     */
    public int deleteSkuInfoBySkuIds(Long[] skuIds) {
        return skuInfoMapper.deleteSkuInfoBySkuIds(skuIds);
    }

    /**
     * 批量逻辑删除sku信息
     *
     * @param skuInfoIds sku信息
     * @return 结果
     */
    public int deleteIds(List<Long> skuInfoIds) {
        if (CollectionUtils.isEmpty(skuInfoIds)) {
            return 0;
        }
        QueryWrapper<SkuInfo> queryWrapper = new QueryWrapper<SkuInfo>();
        queryWrapper.in("skuId", skuInfoIds);
        return skuInfoMapper.delete(queryWrapper);
    }


    /**
     * 逻辑删除sku信息信息
     *
     * @param skuInfo skuInfo 主键
     * @return 结果
     */
    public int delete(SkuInfo skuInfo) {
        QueryWrapper<SkuInfo> queryWrapper = new QueryWrapper<SkuInfo>(skuInfo);
        return skuInfoMapper.delete(queryWrapper);
    }
}
