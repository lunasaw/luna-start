package com.luna.web.controller.user.controller;

import com.luna.common.annotation.Log;
import com.luna.common.core.controller.BaseController;
import com.luna.common.core.domain.AjaxResult;
import com.luna.common.core.page.TableDataInfo;
import com.luna.common.enums.BusinessType;
import com.luna.common.utils.poi.ExcelUtil;
import com.luna.web.controller.user.domain.MemberExtra;
import com.luna.web.controller.user.service.IMemberExtraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 会员信息Controller
 * 
 * @author luna
 * @date 2022-04-30
 */
@RestController
@RequestMapping("/user/extra")
public class MemberExtraController extends BaseController {
    @Autowired
    private IMemberExtraService memberExtraService;

    /**
     * 查询会员信息列表
     */
    @PreAuthorize("@ss.hasPermi('user:extra:list')")
    @GetMapping("/list")
    public TableDataInfo list(MemberExtra memberExtra) {
        startPage();
        List<MemberExtra> list = memberExtraService.selectMemberExtraList(memberExtra);
        return getDataTable(list);
    }

    /**
     * 导出会员信息列表
     */
    @PreAuthorize("@ss.hasPermi('user:extra:export')")
    @Log(title = "会员信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, MemberExtra memberExtra) {
        List<MemberExtra> list = memberExtraService.selectMemberExtraList(memberExtra);
        ExcelUtil<MemberExtra> util = new ExcelUtil<MemberExtra>(MemberExtra.class);
        util.exportExcel(response, list, "会员信息数据");
    }

    /**
     * 获取会员信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('user:extra:query')")
    @GetMapping(value = "/{userId}")
    public AjaxResult getInfo(@PathVariable("userId") Long userId) {
        return AjaxResult.success(memberExtraService.selectMemberExtraByUserId(userId));
    }

    /**
     * 新增会员信息
     */
    @PreAuthorize("@ss.hasPermi('user:extra:add')")
    @Log(title = "会员信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody MemberExtra memberExtra) {
        return toAjax(memberExtraService.insertMemberExtra(memberExtra));
    }

    /**
     * 修改会员信息
     */
    @PreAuthorize("@ss.hasPermi('user:extra:edit')")
    @Log(title = "会员信息", businessType = BusinessType.UPDATE)
    @PutMapping()
    public AjaxResult edit(@RequestBody MemberExtra memberExtra) {
        return toAjax(memberExtraService.updateMemberExtra(memberExtra));
    }

    /**
     * 修改会员状态
     */
    @PreAuthorize("@ss.hasPermi('user:extra:edit')")
    @Log(title = "会员信息", businessType = BusinessType.UPDATE)
    @PutMapping("/{userId}")
    public AjaxResult editMemberFlag(@RequestBody MemberExtra memberExtra) {
        return toAjax(memberExtraService.updateMemberExtra(memberExtra));
    }

    /**
     * 删除会员信息
     */
    @PreAuthorize("@ss.hasPermi('user:extra:remove')")
    @Log(title = "会员信息", businessType = BusinessType.DELETE)
    @DeleteMapping("/{userIds}")
    public AjaxResult remove(@PathVariable Long[] userIds) {
        return toAjax(memberExtraService.deleteMemberExtraByUserIds(userIds));
    }
}
