package com.luna.web.controller.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.luna.web.controller.user.domain.MemberExtra;

import java.util.List;

/**
 * 会员信息Mapper接口
 * 
 * @author luna
 * @date 2022-04-30
 */
public interface MemberExtraMapper extends BaseMapper<MemberExtra> {
    /**
     * 查询会员信息
     * 
     * @param userId 会员信息主键
     * @return 会员信息
     */
    public MemberExtra selectMemberExtraByUserId(Long userId);

    /**
     * 查询会员信息列表
     * 
     * @param memberExtra 会员信息
     * @return 会员信息集合
     */
    public List<MemberExtra> selectMemberExtraList(MemberExtra memberExtra);

    /**
     * 新增会员信息
     * 
     * @param memberExtra 会员信息
     * @return 结果
     */
    public int insertMemberExtra(MemberExtra memberExtra);

    /**
     * 修改会员信息
     * 
     * @param memberExtra 会员信息
     * @return 结果
     */
    public int updateMemberExtra(MemberExtra memberExtra);

    /**
     * 删除会员信息
     * 
     * @param userId 会员信息主键
     * @return 结果
     */
    public int deleteMemberExtraByUserId(Long userId);

    /**
     * 批量删除会员信息
     * 
     * @param userIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteMemberExtraByUserIds(Long[] userIds);
}
