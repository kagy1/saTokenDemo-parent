package com.kagy.satokendemoweb.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kagy.satokendemoweb.entity.RoleParam;
import com.kagy.satokendemoweb.entity.SysRole;
import com.kagy.satokendemoweb.service.SysRoleService;
import com.kagy.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/role")
public class SysController {
    @Autowired
    private SysRoleService sysRoleService;

    // 新增
    @PostMapping
    public Result add(@RequestBody SysRole sysRole) {
        if (sysRoleService.save(sysRole)) {
            return Result.success("新增成功！");
        }
        return Result.error("新增失败！");
    }

    // 编辑
    @PutMapping
    public Result edit(@RequestBody SysRole sysRole) {
        if (sysRoleService.save(sysRole)) {
            return Result.success("编辑成功！");
        }
        return Result.error("编辑失败！");
    }

    // 删除
    @DeleteMapping("/{roleId}")
    public Result delete(@PathVariable("roleId") Long roleId) {
        if (sysRoleService.removeById(roleId)) {
            return Result.success("删除成功！");
        }
        return Result.error("删除失败！");
    }

    // 列表
    @GetMapping("/getList")
    public Result getList(RoleParam roleParam) {
        IPage<SysRole> page = new Page<>(roleParam.getCurrentPage(), roleParam.getPageSize());
        QueryWrapper<SysRole> sysRoleQueryWrapper = new QueryWrapper<>();
        if (StrUtil.isNotBlank(roleParam.getRoleName())) {
            sysRoleQueryWrapper.lambda().like(SysRole::getRoleName, roleParam.getRoleName());
        }
        IPage<SysRole> list = sysRoleService.page(page, sysRoleQueryWrapper);
        return Result.success(list);
    }
}
