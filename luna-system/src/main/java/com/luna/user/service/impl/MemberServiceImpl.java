package com.luna.user.service.impl;

import com.luna.common.utils.DateUtils;
import com.luna.user.domain.MemberDO;
import com.luna.user.mapper.MemberMapper;
import com.luna.user.service.IMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 会员管理Service业务层处理
 * 
 * @author luna
 * @date 2022-04-29
 */
@Service
public class MemberServiceImpl  implements IMemberService {
    @Autowired
    private MemberMapper memberMapper;

    /**
     * 查询会员管理
     * 
     * @param userId 会员管理主键
     * @return 会员管理
     */
    @Override
    public MemberDO selectMemberByUserId(Long userId) {
        return memberMapper.selectMemberByUserId(userId);
    }

    /**
     * 查询会员管理列表
     * 
     * @param memberDO 会员管理
     * @return 会员管理
     */
    @Override
    public List<MemberDO> selectMemberDOList(MemberDO memberDO) {
        return memberMapper.selectMemberDOList(memberDO);
    }

    /**
     * 新增会员管理
     * 
     * @param memberDO 会员管理
     * @return 结果
     */
    @Override
    public int insertMemberDO(MemberDO memberDO) {
        memberDO.setCreateTime(DateUtils.getNowDate());
        return memberMapper.insertMemberDO(memberDO);
    }

    /**
     * 修改会员管理
     * 
     * @param memberDO 会员管理
     * @return 结果
     */
    @Override
    public int updateMemberDO(MemberDO memberDO) {
        memberDO.setUpdateTime(DateUtils.getNowDate());
        return memberMapper.updateMemberDO(memberDO);
    }

    /**
     * 批量删除会员管理
     * 
     * @param userIds 需要删除的会员管理主键
     * @return 结果
     */
    @Override
    public int deleteMemberByUserIds(Long[] userIds) {
        return memberMapper.deleteMemberByUserIds(userIds);
    }

    /**
     * 删除会员管理信息
     * 
     * @param userId 会员管理主键
     * @return 结果
     */
    @Override
    public int deleteMemberByUserId(Long userId) {
        return memberMapper.deleteMemberByUserId(userId);
    }
}
