package com.luna.web.controller.user;

import com.luna.common.annotation.Log;
import com.luna.common.core.controller.BaseController;
import com.luna.common.core.domain.AjaxResult;
import com.luna.common.core.page.TableDataInfo;
import com.luna.common.enums.BusinessType;

import com.luna.common.utils.poi.ExcelUtil;
import com.luna.user.domain.MemberDO;
import com.luna.user.service.IMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 会员管理Controller
 * 
 * @author luna
 * @date 2022-04-29
 */
@RestController
@RequestMapping("/user/member")
public class MemberController extends BaseController
{
    @Autowired
    private IMemberService memberDOService;

    /**
     * 查询会员管理列表
     */
    @PreAuthorize("@ss.hasPermi('user:member:list')")
    @GetMapping("/list")
    public TableDataInfo list(MemberDO memberDO)
    {
        startPage();
        List<MemberDO> list = memberDOService.selectMemberDOList(memberDO);
        return getDataTable(list);
    }

    /**
     * 导出会员管理列表
     */
    @PreAuthorize("@ss.hasPermi('user:member:export')")
    @Log(title = "会员管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, MemberDO memberDO)
    {
        List<MemberDO> list = memberDOService.selectMemberDOList(memberDO);
        ExcelUtil<MemberDO> util = new ExcelUtil<MemberDO>(MemberDO.class);
        util.exportExcel(response, list, "会员管理数据");
    }

    /**
     * 获取会员管理详细信息
     */
    @PreAuthorize("@ss.hasPermi('user:member:query')")
    @GetMapping(value = "/{userId}")
    public AjaxResult getInfo(@PathVariable("userId") Long userId)
    {
        return AjaxResult.success(memberDOService.selectMemberByUserId(userId));
    }

    /**
     * 新增会员管理
     */
    @PreAuthorize("@ss.hasPermi('user:member:add')")
    @Log(title = "会员管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody MemberDO memberDO)
    {
        return toAjax(memberDOService.insertMemberDO(memberDO));
    }

    /**
     * 修改会员管理
     */
    @PreAuthorize("@ss.hasPermi('user:member:edit')")
    @Log(title = "会员管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody MemberDO memberDO)
    {
        return toAjax(memberDOService.updateMemberDO(memberDO));
    }


    /**
     * 修改会员状态
     */
    @PreAuthorize("@ss.hasPermi('user:member:edit')")
    @Log(title = "会员管理", businessType = BusinessType.UPDATE)
    @PutMapping("/{userId}")
    public AjaxResult editDelFlag(@RequestBody MemberDO memberDO)
    {
        return toAjax(memberDOService.updateMemberDO(memberDO));
    }

    /**
     * 删除会员管理
     */
    @PreAuthorize("@ss.hasPermi('user:member:remove')")
    @Log(title = "会员管理", businessType = BusinessType.DELETE)
	@DeleteMapping("/{userIds}")
    public AjaxResult remove(@PathVariable Long[] userIds)
    {
        return toAjax(memberDOService.deleteMemberByUserIds(userIds));
    }
}
