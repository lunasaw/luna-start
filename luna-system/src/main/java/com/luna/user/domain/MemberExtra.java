package com.luna.user.domain;

import com.luna.common.annotation.Excel;
import com.luna.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 会员信息对象 tb_member_extra
 * 
 * @author luna
 * @date 2022-04-30
 */
public class MemberExtra extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 用户ID */
    private Long userId;

    /** 扩展字段 */
    private String extend;

    /** 删除标志 */
    @Excel(name = "删除标志")
    private String delFlag;

    /** 用户头像 */
    @Excel(name = "用户头像")
    private String headImg;

    public void setUserId(Long userId) 
    {
        this.userId = userId;
    }

    public Long getUserId() 
    {
        return userId;
    }
    public void setExtend(String extend) 
    {
        this.extend = extend;
    }

    public String getExtend() 
    {
        return extend;
    }
    public void setDelFlag(String delFlag) 
    {
        this.delFlag = delFlag;
    }

    public String getDelFlag() 
    {
        return delFlag;
    }
    public void setHeadImg(String headImg) 
    {
        this.headImg = headImg;
    }

    public String getHeadImg() 
    {
        return headImg;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("userId", getUserId())
            .append("extend", getExtend())
            .append("delFlag", getDelFlag())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .append("headImg", getHeadImg())
            .toString();
    }
}
