package com.kagy.satokendemoweb.controller;

import com.kagy.satokendemoweb.service.SysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/role")
public class SysController {
    @Autowired
    private SysRoleService sysRoleService;

    // 新增
//    @PostMapping
//    public ResultVo add() {
//
//    }
}
