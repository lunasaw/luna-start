package com.luna.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.luna.common.utils.DateUtils;
import com.luna.user.domain.MemberExtra;
import com.luna.user.mapper.MemberExtraMapper;
import com.luna.user.service.IMemberExtraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 会员信息Service业务层处理
 * 
 * @author luna
 * @date 2022-04-30
 */
@Service
public class MemberExtraServiceImpl extends ServiceImpl<MemberExtraMapper, MemberExtra> implements IMemberExtraService {
    @Autowired
    private MemberExtraMapper memberExtraMapper;

    /**
     * 查询会员信息
     * 
     * @param userId 会员信息主键
     * @return 会员信息
     */
    @Override
    public MemberExtra selectMemberExtraByUserId(Long userId) {
        return memberExtraMapper.selectMemberExtraByUserId(userId);
    }

    /**
     * 查询会员信息列表
     * 
     * @param memberExtra 会员信息
     * @return 会员信息
     */
    @Override
    public List<MemberExtra> selectMemberExtraList(MemberExtra memberExtra) {
        return memberExtraMapper.selectMemberExtraList(memberExtra);
    }

    /**
     * 新增会员信息
     * 
     * @param memberExtra 会员信息
     * @return 结果
     */
    @Override
    public int insertMemberExtra(MemberExtra memberExtra) {
        memberExtra.setCreateTime(DateUtils.getNowDate());
        return memberExtraMapper.insertMemberExtra(memberExtra);
    }

    /**
     * 修改会员信息
     * 
     * @param memberExtra 会员信息
     * @return 结果
     */
    @Override
    public int updateMemberExtra(MemberExtra memberExtra) {
        memberExtra.setUpdateTime(DateUtils.getNowDate());
        return memberExtraMapper.updateMemberExtra(memberExtra);
    }

    /**
     * 批量删除会员信息
     * 
     * @param userIds 需要删除的会员信息主键
     * @return 结果
     */
    @Override
    public int deleteMemberExtraByUserIds(Long[] userIds) {
        return memberExtraMapper.deleteMemberExtraByUserIds(userIds);
    }

    /**
     * 删除会员信息信息
     * 
     * @param userId 会员信息主键
     * @return 结果
     */
    @Override
    public int deleteMemberExtraByUserId(Long userId) {
        return memberExtraMapper.deleteMemberExtraByUserId(userId);
    }
}
