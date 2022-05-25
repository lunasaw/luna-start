package com.luna.product.service;

import java.util.*;

import com.github.pagehelper.PageInfo;
import com.luna.common.utils.DateUtils;
import com.luna.common.utils.StringUtils;
import com.luna.product.domain.AttributeCategory;
import com.luna.product.domain.req.AttributeReq;
import com.luna.product.mapper.AttributeCategoryMapper;
import com.luna.utils.DO2VOUtils;
import com.luna.utils.Req2DOUtils;
import org.apache.commons.collections4.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.function.Function;
import java.util.stream.Collectors;

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

/**
 * 商品属性参数Service业务层处理
 *
 * @author luna
 * @date 2022-05-15
 */
@Service
public class AttributeService extends ServiceImpl<AttributeMapper, Attribute> {
    @Autowired
    private AttributeMapper         attributeMapper;

    @Autowired
    private AttributeCategoryMapper attributeCategoryMapper;

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
        List<AttributeVO> attributeVOList = list.stream().map(record -> {
            AttributeCategory attributeCategory = attributeCategoryMapper.selectAttributeCategoryById(record.getProductAttributeCategoryId());
            String name = Optional.ofNullable(attributeCategory).map(AttributeCategory::getName).orElse(StringUtils.EMPTY);
            return DO2VOUtils.attribute2AttributeVO(record, name);
        }).collect(Collectors.toList());
        PageInfo pageInfo = new PageInfo<>(list);
        pageInfo.setList(attributeVOList);
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
     * @param attributeReq 商品属性参数
     * @param page 分页参数
     * @return 商品属性参数
     */
    public IPage<AttributeVO> selectVOList(IPage<Attribute> page, AttributeReq attributeReq) {
        Long categoryId = attributeReq.getCategoryId();
        Attribute attribute = Req2DOUtils.attributeReq2Attribute(attributeReq);
        QueryWrapper<Attribute> queryWrapper = new QueryWrapper<Attribute>(attribute);
        if (StringUtils.isNotEmpty(attributeReq.getName())) {
            queryWrapper.like("name", attributeReq.getName());
            attribute.setName(null);
        }
        List<AttributeCategory> attributeCategories = Lists.newArrayList();
        if (Objects.nonNull(categoryId)) {
            AttributeCategory attributeCategory = new AttributeCategory();
            attributeCategory.setCategoryId(categoryId);
            attributeCategories = attributeCategoryMapper.selectList(new QueryWrapper<>(attributeCategory));
            if (CollectionUtils.isEmpty(attributeCategories)) {
                return Page.of(0, 0);
            }
            List<Long> attributeCategoryIds = attributeCategories.stream().map(AttributeCategory::getId).collect(Collectors.toList());
            queryWrapper.in("product_attribute_category_id", attributeCategoryIds);
        }
        for (OrderItem order : page.orders()) {
            queryWrapper.orderBy(true, order.isAsc(), order.getColumn());
        }
        Page<Attribute> selectPage = Page.of(page.getCurrent(), page.getSize());
        selectPage.setMaxLimit(page.maxLimit());
        // 构造VO
        List<AttributeVO> list = Lists.newArrayList();
        Page<Attribute> attributePage = attributeMapper.selectPage(selectPage, queryWrapper);
        List<Attribute> records = attributePage.getRecords();

        // 条件查询Map直接获取
        Map<Long, AttributeCategory> categoryMap =
            attributeCategories.stream().collect(Collectors.toMap(AttributeCategory::getId, Function.identity()));
        for (Attribute record : records) {
            // Map为空 再查一次
            AttributeCategory attributeCategory = categoryMap.get(record.getProductAttributeCategoryId());
            String categoryAttributeName = StringUtils.EMPTY;
            if (attributeCategory != null) {
                categoryAttributeName = attributeCategory.getName();
            } else {
                attributeCategory = attributeCategoryMapper.selectAttributeCategoryById(record.getProductAttributeCategoryId());
                categoryAttributeName = Optional.ofNullable(attributeCategory).map(AttributeCategory::getName).orElse(StringUtils.EMPTY);
            }
            AttributeVO attributeVO = DO2VOUtils.attribute2AttributeVO(record, categoryAttributeName);
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
        return attributeMapper.delete(queryWrapper);
    }
}
