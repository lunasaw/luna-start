package com.luna.product.service;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.luna.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import org.apache.commons.compress.utils.Lists;
import org.apache.commons.collections4.CollectionUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.luna.product.mapper.AttributeMapper;
import com.luna.product.domain.Attribute;
import com.luna.product.domain.vo.AttributeVO;
import com.luna.product.utils.DO2VOUtils;

/**
 * 商品属性参数Service业务层处理
 *
 * @author luna
 * @date 2022-05-15
 */
@Service
public class AttributeService extends ServiceImpl<AttributeMapper, Attribute> {
    @Autowired
    private AttributeMapper attributeMapper;

    /**
     * 查询商品属性参数
     *
     * @param id 商品属性参数主键
     * @return 商品属性参数
     */
    public Attribute selectAttributeById(Long id) {
        return attributeMapper.selectAttributeById(id);
    }

    /**
     * 查询商品属性参数列表
     *
     * @param attribute 商品属性参数
     * @return 商品属性参数
     */
    public PageInfo selectAttributeList(Attribute attribute) {
        List<Attribute> list = attributeMapper.selectAttributeList(attribute);
        PageInfo pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    /**
     * 查询全部商品属性参数列表
     *
     * @param attribute 商品属性参数
     * @return 商品属性参数
     */
    public List<Attribute> selectAllList(Attribute attribute) {

        QueryWrapper<Attribute> queryWrapper = new QueryWrapper<Attribute>(attribute);
        queryWrapper.last("limit 100");
        ArrayList<Attribute> list = Lists.newArrayList();
        while (true) {
            List<Attribute> attributes = attributeMapper.selectList(queryWrapper);
            if (CollectionUtils.isEmpty(attributes)) {
                break;
            }
            list.addAll(attributes);
        }

        return list;
    }

    /**
     * ids查询品牌列表
     *
     * @param ids 商品属性参数
     * @return 商品属性参数
     */
    public List<Attribute> selectAttributeByIds(List<Long> ids) {
        if (CollectionUtils.isEmpty(ids)) {
            return new ArrayList<>();
        }
        return attributeMapper.selectBatchIds(ids);
    }

    /**
     * 分页查询商品属性参数列表
     *
     * @param attribute 商品属性参数
     * @param page 分页参数
     * @return 商品属性参数
     */
    public IPage<Attribute> selectList(IPage<Attribute> page, Attribute attribute) {
        QueryWrapper<Attribute> queryWrapper = new QueryWrapper<Attribute>(attribute);
        for (OrderItem order : page.orders()) {
            queryWrapper.orderBy(true, order.isAsc(), order.getColumn());
        }
        Page<Attribute> selectPage = Page.of(page.getCurrent(), page.getSize());
        selectPage.setMaxLimit(page.maxLimit());
        return attributeMapper.selectPage(selectPage, queryWrapper);
    }

    /**
     * 分页查询商品属性参数VO视图列表
     *
     * @param attribute 商品属性参数
     * @param page 分页参数
     * @return 商品属性参数
     */
    public IPage<AttributeVO> selectVOList(IPage<Attribute> page, Attribute attribute) {
        IPage<Attribute> attributePage = selectList(page, attribute);
        List<AttributeVO> list = new ArrayList<>();
        List<Attribute> records = attributePage.getRecords();

        for (Attribute record : records) {

            AttributeVO attributeVO = DO2VOUtils.attribute2AttributeVO(record);
            list.add(attributeVO);
        }
        Page<AttributeVO> result = new Page<>(attributePage.getCurrent(), attributePage.getSize(), attributePage.getTotal());
        result.setRecords(list);
        return result;
    }

    /**
     * 新增商品属性参数
     *
     * @param attribute 商品属性参数
     * @return 结果
     */
    public int insertAttribute(Attribute attribute) {
        attribute.setCreateTime(DateUtils.getNowDate());
        return attributeMapper.insertAttribute(attribute);
    }

    /**
     * 修改商品属性参数
     *
     * @param attribute 商品属性参数
     * @return 结果
     */
    public int updateAttribute(Attribute attribute) {
        attribute.setUpdateTime(DateUtils.getNowDate());
        return attributeMapper.updateAttribute(attribute);
    }

    /**
     * 批量删除商品属性参数
     *
     * @param ids 需要删除的商品属性参数主键
     * @return 结果
     */
    public int deleteAttributeByIds(Long[] ids) {
        return attributeMapper.deleteAttributeByIds(ids);
    }

    /**
     * 逻辑删除商品属性参数信息
     *
     * @param attribute attribute 主键
     * @return 结果
     */
    public int deleteById(Attribute attribute) {
        QueryWrapper<Attribute> queryWrapper = new QueryWrapper<Attribute>(attribute);
        return attributeMapper.deleteById(queryWrapper);
    }
}
