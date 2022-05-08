package com.luna.web.controller.user.service;

import com.luna.web.controller.user.domain.MemberDO;

import java.util.List;

/**
 * 会员管理Service接口
 * 
 * @author luna
 * @date 2022-04-29
 */
public interface IMemberService  {
    /**
     * 查询会员管理
     * 
     * @param userId 会员管理主键
     * @return 会员管理
     */
    public MemberDO selectMemberByUserId(Long userId);

    /**
     * 查询会员管理列表
     * 
     * @param memberDO 会员管理
     * @return 会员管理集合
     */
    public List<MemberDO> selectMemberDOList(MemberDO memberDO);

    /**
     * 新增会员管理
     * 
     * @param memberDO 会员管理
     * @return 结果
     */
    public int insertMemberDO(MemberDO memberDO);

    /**
     * 修改会员管理
     * 
     * @param memberDO 会员管理
     * @return 结果
     */
    public int updateMemberDO(MemberDO memberDO);

    /**
     * 批量删除会员管理
     * 
     * @param userIds 需要删除的会员管理主键集合
     * @return 结果
     */
    public int deleteMemberByUserIds(Long[] userIds);

    /**
     * 删除会员管理信息
     * 
     * @param userId 会员管理主键
     * @return 结果
     */
    public int deleteMemberByUserId(Long userId);
}
