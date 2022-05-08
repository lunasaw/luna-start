package com.luna.web.controller.user.mapper;

import com.luna.web.controller.user.domain.MemberDO;

import java.util.List;

/**
 * 会员管理Mapper接口
 * 
 * @author luna
 * @date 2022-04-29
 */
public interface MemberMapper
{
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
     * 删除会员管理
     * 
     * @param userId 会员管理主键
     * @return 结果
     */
    public int deleteMemberByUserId(Long userId);

    /**
     * 批量删除会员管理
     * 
     * @param userIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteMemberByUserIds(Long[] userIds);
}
