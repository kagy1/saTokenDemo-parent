package com.kagy.satokendemoweb.controller;

import com.kagy.satokendemoweb.entity.SysRole;
import com.kagy.satokendemoweb.service.SysRoleService;
import com.kagy.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
