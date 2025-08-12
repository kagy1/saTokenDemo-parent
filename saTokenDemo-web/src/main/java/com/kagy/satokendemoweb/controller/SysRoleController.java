package com.kagy.satokendemoweb.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kagy.satokendemoweb.entity.RoleParam;
import com.kagy.satokendemoweb.entity.SaveMenyParm;
import com.kagy.satokendemoweb.entity.SelectItem;
import com.kagy.satokendemoweb.entity.SysRole;
import com.kagy.satokendemoweb.service.SysRoleService;
import com.kagy.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/role")
public class SysRoleController {
    @Autowired
    private SysRoleService sysRoleService;

    // 新增
    @PostMapping
    public Result add(@RequestBody SysRole sysRole) {
        sysRole.setCreateTime(LocalDateTime.now());
        if (sysRoleService.save(sysRole)) {
            return Result.success("新增成功！");
        }
        return Result.error("新增失败！");
    }

    // 编辑
    @PutMapping
    public Result edit(@RequestBody SysRole sysRole) {
        if (sysRoleService.updateById(sysRole)) {
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

    // 角色下拉数据
    @PostMapping("/selectList")
    public Result selectList() {
        List<SysRole> list = sysRoleService.list();
        // 返回下拉数据
        List<SelectItem> selectItems = new ArrayList<>();
        list.stream().forEach(item -> {
            SelectItem vo = new SelectItem();
            vo.setCheck(false);
            vo.setLabel(item.getRoleName());
            vo.setValue(item.getRoleId());
            selectItems.add(vo);
        });
        return Result.success("查询成功", selectItems);
    }

    // 保存角色菜单
    @PostMapping("/saveRoleMenu")
    public Result saveRoleMenu(@RequestBody SaveMenyParm saveMenyParm) {

        return null;
    }
}
