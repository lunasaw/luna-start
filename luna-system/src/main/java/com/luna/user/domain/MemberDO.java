package com.luna.user.domain;

import com.luna.common.annotation.Excel;
import com.luna.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 会员管理对象 tb_member
 * 
 * @author luna
 * @date 2022-04-29
 */
public class MemberDO extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 自增id */
    private Long userId;

    /** 登录名 */
    @Excel(name = "登录名")
    private String username;

    /** qq官方唯一编号信息 */
    @Excel(name = "qq官方唯一编号信息")
    private String qqOpenId;

    /** qq */
    @Excel(name = "qq")
    private String qqNumber;

    /** 登录密码 */
    @Excel(name = "登录密码")
    private String password;

    /** 邮箱 */
    @Excel(name = "邮箱")
    private String email;

    /** 新用户是否已经通过邮箱激活帐号 0 激活 1 未激活 */
    @Excel(name = "新用户是否已经通过邮箱激活帐号 0 激活 1 未激活")
    private String isActive;

    /** 用户性别（0男 1女 2未知） */
    @Excel(name = "用户性别", readConverterExp = "0=男,1=女,2=未知")
    private String sex;

    /** 手机 */
    @Excel(name = "手机")
    private String telephone;

    /** 学历  小学 1 初中 2 高中 3 专科 4 本科 5 硕士 6 博士 7 */
    @Excel(name = "学历  小学 1 初中 2 高中 3 专科 4 本科 5 硕士 6 博士 7")
    private String education;

    /** 爱好 */
    @Excel(name = "爱好")
    private String hobby;

    /** 简介 */
    @Excel(name = "简介")
    private String introduce;

    /** 帐号状态（0正常 1停用） */
    @Excel(name = "帐号状态", readConverterExp = "0=正常,1=停用")
    private String status;

    /** 删除标志（0代表存在 1代表删除） */
    private String delFlag;

    public void setUserId(Long userId) 
    {
        this.userId = userId;
    }

    public Long getUserId() 
    {
        return userId;
    }
    public void setUsername(String username) 
    {
        this.username = username;
    }

    public String getUsername() 
    {
        return username;
    }
    public void setQqOpenId(String qqOpenId) 
    {
        this.qqOpenId = qqOpenId;
    }

    public String getQqOpenId() 
    {
        return qqOpenId;
    }
    public void setQqNumber(String qqNumber) 
    {
        this.qqNumber = qqNumber;
    }

    public String getQqNumber() 
    {
        return qqNumber;
    }
    public void setPassword(String password) 
    {
        this.password = password;
    }

    public String getPassword() 
    {
        return password;
    }
    public void setEmail(String email) 
    {
        this.email = email;
    }

    public String getEmail() 
    {
        return email;
    }
    public void setIsActive(String isActive) 
    {
        this.isActive = isActive;
    }

    public String getIsActive() 
    {
        return isActive;
    }
    public void setSex(String sex) 
    {
        this.sex = sex;
    }

    public String getSex() 
    {
        return sex;
    }
    public void setTelephone(String telephone) 
    {
        this.telephone = telephone;
    }

    public String getTelephone() 
    {
        return telephone;
    }
    public void setEducation(String education) 
    {
        this.education = education;
    }

    public String getEducation() 
    {
        return education;
    }
    public void setHobby(String hobby) 
    {
        this.hobby = hobby;
    }

    public String getHobby() 
    {
        return hobby;
    }
    public void setIntroduce(String introduce) 
    {
        this.introduce = introduce;
    }

    public String getIntroduce() 
    {
        return introduce;
    }
    public void setStatus(String status) 
    {
        this.status = status;
    }

    public String getStatus() 
    {
        return status;
    }
    public void setDelFlag(String delFlag) 
    {
        this.delFlag = delFlag;
    }

    public String getDelFlag() 
    {
        return delFlag;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("userId", getUserId())
            .append("username", getUsername())
            .append("qqOpenId", getQqOpenId())
            .append("qqNumber", getQqNumber())
            .append("password", getPassword())
            .append("email", getEmail())
            .append("isActive", getIsActive())
            .append("sex", getSex())
            .append("telephone", getTelephone())
            .append("education", getEducation())
            .append("hobby", getHobby())
            .append("introduce", getIntroduce())
            .append("status", getStatus())
            .append("delFlag", getDelFlag())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
